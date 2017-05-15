package mju.ntj.ejis.commons.test;

import java.util.Date;

import mju.ntj.ejis.commons.annotations.RequireRetry;

import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("printTexto")
public class PrintTexto {

	private int i = 0;

	@RequireRetry
	public void printTexto() throws Exception {
		System.out.println("Hola" + new Date());
		throw new Exception();
	}

	@RequireRetry
	public void printTextoWithNoRetryCode() throws Exception {
		System.out.println("NoRetryCode" + new Date());
		i++;
		if (i % 2 == 0)
			throw new Exception("NO_RETRY");
		else
			throw new Exception("NO_ATTEMPT");

	}

}
