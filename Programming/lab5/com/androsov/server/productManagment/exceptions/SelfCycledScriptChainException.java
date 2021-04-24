package com.androsov.server.productManagment.exceptions;

public class SelfCycledScriptChainException extends Exception {
    public SelfCycledScriptChainException(String message) {
        super(message);
    }
}
