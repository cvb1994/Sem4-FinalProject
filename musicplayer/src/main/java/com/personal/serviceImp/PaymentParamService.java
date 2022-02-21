package com.personal.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.common.PaymentTimeUnit;
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
	public List<PaymentParamDto> getAll() {
		return paymentParamRepo.findAll().stream().map(paymentParamMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public PaymentParamDto getById(int id) {
		return paymentParamRepo.findById(id).map(paymentParamMapper::entityToDto).orElse(null);
	}

	@Override
	public ResponseDto save(PaymentParamDto model) {
		ResponseDto res = new ResponseDto();
		
		PaymentParam param = Optional.ofNullable(model).map(paymentParamMapper::dtoToEntity).orElse(null);
		if(param == null) {
			res.setError("Dữ liệu không đúng");
			res.setIsSuccess(false);
			return res;
		}
		String unitCheck = param.getUnit();
		if(!PaymentTimeUnit.DAY.name.equalsIgnoreCase(unitCheck) && !PaymentTimeUnit.MONTH.name.equalsIgnoreCase(unitCheck) &&
				!PaymentTimeUnit.YEAR.name.equalsIgnoreCase(unitCheck)) {
			res.setError("Đơn vị thời gian không đúng");
			res.setIsSuccess(false);
			return res;
		}
		
		PaymentParam savedParam = paymentParamRepo.save(param);
		if(savedParam != null) {
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không thể tạo tham số hệ thống mới");
		res.setIsSuccess(false);
		return res;
	}

	@Override
	public ResponseDto delete(int id) {
		ResponseDto res = new ResponseDto();
		Optional<PaymentParam> optParam = paymentParamRepo.findById(id);
		if(optParam.isPresent()) {
			PaymentParam param = optParam.get();
			paymentParamRepo.delete(param);
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không tìm thấy tham số hệ thống");
		res.setIsSuccess(false);
		return res;
	}

}
