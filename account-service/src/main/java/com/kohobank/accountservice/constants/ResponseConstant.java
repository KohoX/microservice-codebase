package com.kohobank.accountservice.constants;

import com.kohobank.accountservice.dto.ResponseDto;

public final class ResponseConstant {

    private ResponseConstant(){}

    public static ResponseDto success(){
        return new ResponseDto(ResponseCodesAndMessages.SUCCESS_CODE, ResponseCodesAndMessages.SUCCESS_MESSAGE);
    }

    public static ResponseDto created(){
        return new ResponseDto(ResponseCodesAndMessages.CREATED_CODE, ResponseCodesAndMessages.CREATED_MESSAGE);
    }

    public static ResponseDto badRequest(){
        return new ResponseDto(ResponseCodesAndMessages.BAD_REQUEST_CODE, ResponseCodesAndMessages.BAD_REQUEST_MESSAGE);
    }

    public static ResponseDto unauthorized(){
        return new ResponseDto(ResponseCodesAndMessages.UNAUTHORIZED_CODE, ResponseCodesAndMessages.UNAUTHORIZED_MESSAGE);
    }

    public static ResponseDto forbidden(){
        return new ResponseDto(ResponseCodesAndMessages.FORBIDDEN_CODE, ResponseCodesAndMessages.FORBIDDEN_MESSAGE);
    }

    public static ResponseDto notFound(){
        return new ResponseDto(ResponseCodesAndMessages.NOT_FOUND_CODE, ResponseCodesAndMessages.NOT_FOUND_MESSAGE);
    }

    public static ResponseDto conflict(){
        return new ResponseDto(ResponseCodesAndMessages.CONFLICT_CODE, ResponseCodesAndMessages.CONFLICT_MESSAGE);
    }

    public static ResponseDto internalServerError(){
        return new ResponseDto(ResponseCodesAndMessages.INTERNAL_SERVER_ERROR_CODE, ResponseCodesAndMessages.INTERNAL_SERVER_ERROR_MESSAGE);
    }
}
