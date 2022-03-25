package com.personal.serviceImp;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.personal.dto.PageDto;
import com.personal.dto.PaymentDto;
import com.personal.dto.ProfitReportDto;
import com.personal.dto.ResponseDto;
import com.personal.entity.Payment;
import com.personal.entity.PaymentParam;
import com.personal.entity.User;
import com.personal.mapper.PaymentMapper;
import com.personal.musicplayer.specification.PaymentSpecification;
import com.personal.repository.PaymentParamRepository;
import com.personal.repository.PaymentRepository;
import com.personal.repository.UserRepository;
import com.personal.service.IPaymentService;
import com.personal.utils.Utilities;
import com.personal.utils.VNPayUtils;

@Service
public class PaymentService implements IPaymentService {
	@Autowired
	private VNPayUtils vnPayUtils;
	@Autowired
	private PaymentRepository paymentRepo;
	@Autowired
	private PaymentSpecification paymentSpec;
	@Autowired
	private PaymentParamRepository paymentParamRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PaymentMapper paymentMapper;
	@Autowired
	private Utilities utils;

	@Override
	public ResponseDto sendQuery(PaymentDto model, HttpServletRequest req) throws UnsupportedEncodingException {
		
		String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = model.getOrderInfo();
        String orderType = model.getOrderType();
        String vnp_TxnRef = vnPayUtils.getRandomNumber(8);
        String vnp_IpAddr = vnPayUtils.getIpAddress(req);
        String vnp_TmnCode = vnPayUtils.vnp_TmnCode;
        
        User user = userRepo.findById(model.getUserId()).get();
        
        PaymentParam paymentParam = paymentParamRepo.findById(model.getPaymentParamId()).get();
        
        Payment payment = new Payment();
		payment.setDiscount(paymentParam.getDiscount());
		payment.setPrice(paymentParam.getPrice());
		payment.setActualPrice(paymentParam.getActualPrice());
		payment.setExpireDate(utils.getDateExpire(paymentParam.getTimeExpire(), paymentParam.getUnit()));
		payment.setPaymendMethod(model.getPaymentMethod());
		payment.setTxnCode(vnp_TxnRef);
		payment.setTransCompleted(false);
		payment.setUser(user);
		paymentRepo.save(payment);

        int amount = paymentParam.getActualPrice() * 100;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = model.getBankCode();
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        
        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        
        vnp_Params.put("vnp_ReturnUrl", vnPayUtils.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.0.1 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        
        String queryUrl = query.toString();
        String vnp_SecureHash = vnPayUtils.hmacSHA512(vnPayUtils.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnPayUtils.vnp_PayUrl + "?" + queryUrl;
        ResponseDto res = new ResponseDto();
        res.setStatus(true);
        res.setContent(paymentUrl);
        return res;
	}

	@Override
	public boolean returnPayment(@RequestParam Map<String,String> allParams) {
		String vnp_SecureHash = allParams.get("vnp_SecureHash");
		String vnp_ResponseCode = allParams.get("vnp_ResponseCode");
		
		String txnCode = allParams.get("vnp_TxnRef");
		
		if(allParams.containsKey("vnp_SecureHashType")) {
			allParams.remove("vnp_SecureHashType");
		}
		if (allParams.containsKey("vnp_SecureHash")) {
			allParams.remove("vnp_SecureHash");
        }
		
//		String signValue = vnPayUtils.hashAllFields(allParams);
//		if (signValue.equals(vnp_SecureHash)) {
//			if ("00".equals(vnp_ResponseCode)) {
//				Optional<Payment> optPayment = paymentRepo.findByTxnCode(txnCode);
//				if(optPayment.isPresent()) {
//					Payment payment = optPayment.get();
//					payment.setTransactionCode(allParams.get("vnp_TransactionNo"));
//					payment.setStatusActive(true);
//					paymentRepo.save(payment);
//					return "Thanh toán thành công";
//				}
//				
//				
//			} else {
//				return "Thanh toán không thành công";
//			}
//		}
		
		if ("00".equals(vnp_ResponseCode)) {
			Optional<Payment> optPayment = paymentRepo.findByTxnCode(txnCode);
			if(optPayment.isPresent()) {
				Payment payment = optPayment.get();
				payment.setTransactionCode(allParams.get("vnp_TransactionNo"));
				payment.setTransCompleted(true);
				paymentRepo.save(payment);
				
				User user = payment.getUser();
				user.setVipExpireDate(payment.getExpireDate());
				userRepo.save(user);
				
				return true;
			}
			
			
		} else {
			return false;
		}
		
		return false;
	}

	@Override
	public ResponseDto getAll() {
		ResponseDto res = new ResponseDto();
		List<PaymentDto> list = paymentRepo.findAll().stream().map(paymentMapper::entityToDto).collect(Collectors.toList());
		res.setStatus(true);
		res.setContent(list);
		return res;
	}

	@Override
	public ResponseDto gets(PaymentDto criteria) {
		ResponseDto res = new ResponseDto();
		
		Page<Payment> page = paymentRepo.findAll(paymentSpec.filter(criteria), PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<PaymentDto> list = page.getContent().stream().map(paymentMapper::entityToDto).collect(Collectors.toList());
		
		PageDto pageDto = new PageDto();
		pageDto.setContent(list);
		pageDto.setNumber(page.getNumber());
		pageDto.setNumberOfElements(page.getNumberOfElements());
		pageDto.setPage(page.getNumber());
		pageDto.setSize(page.getSize());
		pageDto.setTotalPages(page.getTotalPages());
		if(page.getNumber() == 0) {
			pageDto.setFirst(true);
		}
		if(page.getNumber() == page.getTotalPages()-1) {
			pageDto.setLast(true);
		}
		res.setStatus(true);
		res.setContent(pageDto);
		return res;
	}

	@Override
	public ResponseDto getById(int id) {
		ResponseDto res = new ResponseDto();
		PaymentDto payment = paymentRepo.findById(id).map(paymentMapper::entityToDto).orElse(null);
		res.setStatus(true);
		res.setContent(payment);
		return res;
	}

	@Override
	public int countPaymentNewInMonth() {
		LocalDateTime current = LocalDateTime.now();
		LocalDateTime start = current.withDayOfMonth(1);
		LocalDateTime end = current.withDayOfMonth(current.getDayOfMonth());
		return paymentRepo.countByCreatedDateBetweenAndTransCompletedTrue(start, end);
	}

	@Override
	public ResponseDto profitInMonths(int month) {
		LocalDateTime current = LocalDateTime.now();
		LocalDateTime start = current.minusMonths(month);
		List<ProfitReportDto> listProfit = new ArrayList<>();
		for(int i = 0; i < month; i++) {
			LocalDateTime time = current.minusMonths(i);
			ProfitReportDto dto = new ProfitReportDto();
			dto.setMonth(time.getMonthValue());
			dto.setYear(time.getYear());
			listProfit.add(dto);
		}
		
		List<Payment> list = paymentRepo.findByCreatedDateBetweenAndTransCompletedTrue(start, current);
		list.forEach(p -> {
			int flag = 0;
			for(int i = 0; i<listProfit.size();i++) {
				if(listProfit.get(i).getMonth() == p.getCreatedDate().getMonthValue()) {
					ProfitReportDto dto = listProfit.get(i);
					dto.setProfit(dto.getProfit()+p.getActualPrice());
					flag = 1;
					break;
				}
			}
			if(flag == 0) {
				ProfitReportDto dto = new ProfitReportDto();
				dto.setMonth(p.getCreatedDate().getMonthValue());
				dto.setYear(p.getCreatedDate().getYear());
				dto.setProfit(p.getActualPrice());
				listProfit.add(dto);
			}
		});
		
		ResponseDto res = new ResponseDto();
		res.setStatus(true);
		res.setContent(listProfit);
		return res;
	}

}
