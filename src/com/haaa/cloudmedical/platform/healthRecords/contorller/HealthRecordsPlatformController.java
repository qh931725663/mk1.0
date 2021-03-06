package com.haaa.cloudmedical.platform.healthRecords.contorller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.common.entity.ResponseDTO;
import com.haaa.cloudmedical.common.util.BeanUtil;
import com.haaa.cloudmedical.entity.CheckReport;
import com.haaa.cloudmedical.entity.ClinicReport;
import com.haaa.cloudmedical.entity.HospitalReport;
import com.haaa.cloudmedical.entity.InsuranceReport;
import com.haaa.cloudmedical.entity.User;
import com.haaa.cloudmedical.platform.healthRecords.service.HealthRecordService;
import com.haaa.cloudmedical.platform.healthRecords.service.HealthRecordsPlatformService;

@Controller
@RequestMapping("/health-record")
public class HealthRecordsPlatformController {

    @Autowired
    private HealthRecordService          service;

    @Autowired
    private HealthRecordsPlatformService platService;

    @RequestMapping("/query.action")
    @ResponseBody
    public Object query(String user_id) {
        Map<String, Object> map = platService.query(user_id);
        return map;
    }

    /*
     * 获取用户指定时间或半年内的所有报告（包括一体机检查数据）
     */
    @RequestMapping("/all.action")
    @ResponseBody
    public Object getReport(String user_id, String report_type, String start, String end) {
        List<Map<String, Object>> list = service.getReport(user_id, report_type, start, end);
        return list;
    }

    /*
     * 分页数据
     */
    @RequestMapping("/pageData.action")
    @ResponseBody
    public Page queryPage(User user, String doctor_name, Integer pageNo) {
        Page page = service.queryPage(user, doctor_name, pageNo);
        return page;
    }

    /**
     * 保存体检报告
     * 
     * @param model
     * @return
     */
    @RequestMapping("/saveCheck.action")
    @ResponseBody
    public Object saveCheck(CheckReport model) {
        ResponseDTO dto = new ResponseDTO();
        Long id = service.saveCheck(model);
        if (id != null) {
            dto.setFlag(true);
            dto.setData(id);
        } else {
            dto.setFlag(false);
            dto.setData(null);
        }
        return dto;
    }

    /**
     * 保存住院报告
     * 
     * @param model
     * @return
     */
    @RequestMapping("/saveHospital.action")
    @ResponseBody
    public Object saveHospital(HospitalReport model) {
        ResponseDTO dto = new ResponseDTO();
        Long id = service.saveHospital(model);
        if (id != null) {
            dto.setFlag(true);
            dto.setData(id);
        } else {
            dto.setFlag(false);
            dto.setData(null);
        }
        return dto;
    }

    /**
     * 保存门诊报告
     * 
     * @param model
     * @return
     */
    @RequestMapping("/saveClinic.action")
    @ResponseBody
    public Object saveClinic(ClinicReport model) {
        ResponseDTO dto = new ResponseDTO();
        Long id = service.saveClinic(model);
        if (id != null) {
            dto.setFlag(true);
            dto.setData(id);
        } else {
            dto.setFlag(false);
            dto.setData(null);
        }
        return dto;
    }

    /**
     * 保存医保记录
     * 
     * @param model
     * @return
     */
    @RequestMapping("/saveMedical.action")
    @ResponseBody
    public Object saveMedical(InsuranceReport model) {
        ResponseDTO dto = new ResponseDTO();
        Long id = service.saveMedical(model);
        if (id != null) {
            dto.setFlag(true);
            dto.setData(id);
        } else {
            dto.setFlag(false);
            dto.setData(null);
        }
        return dto;
    }

    /**
     * 详情
     * 
     * @param model
     * @return
     */
    @RequestMapping("/detail.action")
    @ResponseBody
    public Object detail(String user_id, String pic_type, String start, String end) {
        ResponseDTO dto = new ResponseDTO();
        List<Map<String, Object>> list = service.getReport(user_id, pic_type, start, end);
        dto.setFlag(true);
        dto.setData(list);
        return dto;
    }

    /**
     * 详情
     * 
     * @param model
     * @return
     */
    @RequestMapping("/getHosptialList.action")
    @ResponseBody
    public Object getHosptialList(String user_id, String pic_type, String start, String end) {
        return service.getHosptialList();
    }

    /**
     * 根据医院ID查询科室
     * 
     * @Description:
     * @author 吴琪
     * @param hosp_order_id
     * @return
     * @date: 2017年5月19日 下午5:57:38
     */
    @RequestMapping("/getDepartmentList.action")
    @ResponseBody
    public Object getDepartmentList(String hosp_order_id) {
        return service.getDepartmentList(hosp_order_id);
    }

    @RequestMapping("/getDoctorList.action")
    @ResponseBody
    public Object getDoctorList(String department_order_id) {
        return service.getDoctorList(department_order_id);
    }

    /**
     * 
     * @Description:
     * @author 吴琪
     * @return
     * @date: 2017年5月22日 下午3:40:57
     */
    @RequestMapping("/getOptionContent.action")
    @ResponseBody
    public Object getOptionContent() {
        return service.getOptionContent();
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = multipartResolver.resolveMultipart(request);
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            String path = "";
            while (iter.hasNext()) {
                // 记录上传过程起始时的时间，用来计算上传时间
                int pre = (int) System.currentTimeMillis();
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    // 取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        // 重命名上传后的文件名
                        // String fileName = "demoUpload" +
                        // file.getOriginalFilename();
                        String fileName = StringUtils.join(
                            new String[] { java.util.UUID.randomUUID().toString(), ".", FilenameUtils.getExtension(file.getOriginalFilename()) });
                        // 定义上传路径
                        path = getFilePath(request) + fileName;
                        //
                        String realpath = BeanUtil.getProperty("dbconfig").getString("pictureuploaddir") + path;
                        File localFile = new File(realpath);
                        file.transferTo(localFile);
                    }
                }
                // 记录上传该文件后的时间
                int finaltime = (int) System.currentTimeMillis();
                System.out.println(finaltime - pre);
                // 保存图片
                service.savePicture(request, path);
            }
        }
        return "success";
    }

    private String getFilePath(HttpServletRequest request) {
        // 用户ID
        String user_id = request.getParameter("user_id");
        // 文件类型
        String report_type = request.getParameter("report_type");
        // 文件保存路径
        String separator = "/";
        String dateDir = DateFormatUtils.format(new Date(), "yyyy" + separator + "MM" + separator + "dd");
        String uploadDir = "upload" + separator + report_type + separator + "image" + separator + user_id + separator + dateDir + separator;
        // 文件目录是否存在
        File dir = new File(BeanUtil.getProperty("dbconfig").getString("pictureuploaddir") + uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return uploadDir;
    }

    //查询病历详情
    @RequestMapping("/queryMedicalRecord.action")
    @ResponseBody
    public Object queryMedicalRecord(String order_id) {
        List<Map<String, Object>> list = service.queryMedicalRecord(order_id);
        System.out.println(list);
        return list;
    }
}
