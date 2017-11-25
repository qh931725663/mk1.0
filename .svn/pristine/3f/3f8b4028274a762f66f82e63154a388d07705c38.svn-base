/**
 * 
 */
package com.haaa.cloudmedical.common.entity;

/**
 * @author Bowen Fan
 *
 */
public class StdDTO {

	private int status;
	
	private String msg;
	
	private Object data;
	

	public StdDTO() {
		super();
	}
		
	
	
	/**
	 * @param status
	 */
	public StdDTO(int status) {
		super();
		this.status = status;
	}


	/**
	 * @param status
	 * @param data
	 */
	public StdDTO(int status, Object data) {
		super();
		this.status = status;
		this.data = data;
	}



	/**
	 * @param status
	 * @param msg
	 * @param data
	 */
	public StdDTO(int status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}





	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public static StdDTO setFail(){
		StdDTO stdDTO = new StdDTO();
		stdDTO.setStatus(-1);
		return stdDTO;
	}
	
	public static StdDTO setError(String msg){
		StdDTO stdDTO = new StdDTO();
		stdDTO.setMsg(msg);
		stdDTO.setStatus(-1);
		return stdDTO;
	}
	
	public static StdDTO setSuccess(Object data){
		StdDTO stdDTO = new StdDTO();
		stdDTO.setStatus(0);
		stdDTO.setData(data);
		return stdDTO;
	}
	
}
