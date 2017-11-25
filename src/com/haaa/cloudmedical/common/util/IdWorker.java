package com.haaa.cloudmedical.common.util;


/** 
 * 
 * 分布式id算法snowflake实现的自增id生成器
 * 
 */  
public class IdWorker {  
  
    private Long HORIZON_MILLI=1419242747142L;  
    private final static Integer SEQUENCE_BITS=12;  
    private final static Integer WORK_ID_BITS=5;  
    private final static Integer DATA_CENTER_BITS=5;  
    private final static Integer MILLI_SECOND_BITS=41;  
    private final static Integer MAX_SEQUENCE=-1 ^ (-1<<SEQUENCE_BITS);  
    private final static Integer MAX_WORK_ID=-1 ^ (-1<<WORK_ID_BITS);  
    private final static Integer MAX_DATA_CENTER_ID=-1 ^ (-1 << DATA_CENTER_BITS);  
    private final static Long MAX_MILLISECONDS = -1L ^ (-1L << MILLI_SECOND_BITS);  
  
    private final static Integer MILLI_SHIFT= DATA_CENTER_BITS + WORK_ID_BITS + SEQUENCE_BITS;  
    private final static Integer DATA_CENTER_SHIFT= WORK_ID_BITS + SEQUENCE_BITS;  
    private final static Integer WORK_ID_SHIFT= SEQUENCE_BITS;  
  
  
    private Integer work_id=1;  
    private Integer data_center_id=1;  
  
  
    private long sequence = 0;  
    private long lastTimestamp=0;  
  
    public IdWorker(){
    	
    }
    
    public IdWorker(Integer workId, Integer dataCenterId) {  
        if((workId & MAX_WORK_ID) == 0){  
            throw new RuntimeException("work id is too large");  
        }  
        if((dataCenterId & MAX_DATA_CENTER_ID) == 0){  
            throw new RuntimeException("data center id is too large");  
        }  
        this.work_id = workId;  
        this.data_center_id = dataCenterId;  
    }  
  
    public synchronized long nextId(){  
        long milliseconds = System.currentTimeMillis();  
        if(this.lastTimestamp == milliseconds){  
            this.sequence = (this.sequence+1) & MAX_SEQUENCE;  
            if(this.sequence == 0){  
                milliseconds = tilNextMillis(this.lastTimestamp);  
            }  
        }else if(milliseconds < this.lastTimestamp){  
            throw new RuntimeException("clock wrong");  
        }else {  
            this.sequence = 0;  
        }  
        this.lastTimestamp = milliseconds;  
        if(((milliseconds-HORIZON_MILLI) & MAX_MILLISECONDS) == 0){  
            throw new RuntimeException("time is too large");  
        }  
        long nextId = ((milliseconds-HORIZON_MILLI)<<MILLI_SHIFT)+(this.data_center_id<<DATA_CENTER_SHIFT)+(this.work_id<<WORK_ID_SHIFT)+this.sequence;  
        return nextId;  
    }  
  
    private long tilNextMillis(final long lastTimestamp) {  
        long timestamp = System.currentTimeMillis();  
        while (timestamp <= lastTimestamp) {  
            timestamp = System.currentTimeMillis();  
        }  
        return timestamp;  
    }  

}  



  
