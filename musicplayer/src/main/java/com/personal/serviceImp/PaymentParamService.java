package com.personal.serviceImp;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.personal.common.PaymentTimeUnit;
import com.personal.dto.PageDto;
import com.personal.dto.PaymentParamDto;
import com.personal.dto.ResponseDto;
import com.personal.entity.PaymentParam;
import com.personal.mapper.PaymentParamMapper;
import com.personal.repository.PaymentParamRepository;
import com.personal.service.IPaymentParam;

@Service
public class PaymentParamService implements IPaymentParam {
	@Autowired
	private PaymentParamRepository paymentParamRepo;
	@Autowired
	private PaymentParamMapper paymentParamMapper;

	@Override
	public ResponseDto getAll() {
		ResponseDto res = new ResponseDto();
		List<PaymentParamDto> list = paymentParamRepo.findAll().stream().map(paymentParamMapper::entityToDto).collect(Collectors.toList());
		res.setStatus(true);
		res.setContent(list);
		return res;
	}

	@Override
	public ResponseDto getById(int id) {
		ResponseDto res = new ResponseDto();
		PaymentParamDto param = paymentParamRepo.findById(id).map(paymentParamMapper::entityToDto).orElse(null);
		res.setStatus(true);
		res.setContent(param);
		return res;
	}

	@Override
	public ResponseDto create(PaymentParamDto model) {
		ResponseDto res = new ResponseDto();
		
		PaymentParam param = Optional.ofNullable(model).map(paymentParamMapper::dtoToEntity).orElse(null);
		if(param == null) {
			res.setMessage("Dữ liệu không đúng");
			res.setStatus(false);
			return res;
		}
		
		String unitCheck = param.getUnit();
		if(!PaymentTimeUnit.DAY.name.equalsIgnoreCase(unitCheck) && !PaymentTimeUnit.MONTH.name.equalsIgnoreCase(unitCheck) &&
				!PaymentTimeUnit.YEAR.name.equalsIgnoreCase(unitCheck)) {
			res.setMessage("Đơn vị thời gian không đúng");
			res.setStatus(false);
			return res;
		}
		
		//tinh gia tri thuc te
        BigDecimal a = new BigDecimal(100);
		BigDecimal b = new BigDecimal(model.getDiscount());
		double percentAfterDiscount = (a.subtract(b).doubleValue())/100;
		int priceAfterDiscount = (int) (model.getPrice() * percentAfterDiscount);
		param.setActualPrice(priceAfterDiscount);
		
		PaymentParam savedParam = paymentParamRepo.save(param);
		if(savedParam != null) {
			res.setStatus(true);
			res.setMessage("Tạo mới thành công");
			return res;
		}
		
		res.setMessage("Không thể tạo tham số hệ thống mới");
		res.setStatus(false);
		return res;
	}
	
	@Override
	public ResponseDto update(PaymentParamDto model) {
		ResponseDto res = new ResponseDto();
		
		PaymentParam param = Optional.ofNullable(model).map(paymentParamMapper::dtoToEntity).orElse(null);
		if(param == null) {
			res.setMessage("Dữ liệu không đúng");
			res.setStatus(false);
			return res;
		}
		
		String unitCheck = param.getUnit();
		if(!PaymentTimeUnit.DAY.name.equalsIgnoreCase(unitCheck) && !PaymentTimeUnit.MONTH.name.equalsIgnoreCase(unitCheck) &&
				!PaymentTimeUnit.YEAR.name.equalsIgnoreCase(unitCheck)) {
			res.setMessage("Đơn vị thời gian không đúng");
			res.setStatus(false);
			return res;
		}
		
		PaymentParam savedParam = paymentParamRepo.save(param);
		if(savedParam != null) {
			res.setStatus(true);
			res.setMessage("Cập nhật thành công");
			return res;
		}
		
		res.setMessage("Không thể cập nhật tham số hệ thống mới");
		res.setStatus(false);
		return res;
	}

	@Override
	public ResponseDto delete(int id) {
		ResponseDto res = new ResponseDto();
		Optional<PaymentParam> optParam = paymentParamRepo.findById(id);
		if(optParam.isPresent()) {
			PaymentParam param = optParam.get();
			paymentParamRepo.delete(param);
			res.setStatus(true);
			res.setMessage("Xóa thành công");
			return res;
		}
		
		res.setMessage("Không tìm thấy tham số hệ thống");
		res.setStatus(false);
		return res;
	}

	@Override
	public ResponseDto gets(PaymentParamDto criteria) {
		ResponseDto res = new ResponseDto();
		
		Page<PaymentParam> page = paymentParamRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<PaymentParamDto> list = page.getContent().stream().map(paymentParamMapper::entityToDto).collect(Collectors.toList());
		
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

}
