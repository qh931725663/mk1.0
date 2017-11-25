package com.haaa.cloudmedical.wechat.web;

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

import com.haaa.cloudmedical.common.entity.StdDTO;
import com.haaa.cloudmedical.common.util.BeanUtil;
import com.haaa.cloudmedical.entity.CheckReport;
import com.haaa.cloudmedical.entity.ClinicReport;
import com.haaa.cloudmedical.entity.HospitalReport;
import com.haaa.cloudmedical.entity.InsuranceReport;
import com.haaa.cloudmedical.wechat.service.IWxHealthRecordService;

@Controller
@RequestMapping("/wx/healthRecord")
public class WxHealthRecordController {
    
    @Autowired
    IWxHealthRecordService service;
    
    /**
     * 查询报告
     * @Description: 
     * @author 吴琪
     * @return 
     * @date: 2017年9月7日 下午5:31:44
     */
    @RequestMapping("/queryList.action")
    @ResponseBody
    public Object queryList(HttpServletRequest request){
        String openid = (String) request.getSession().getAttribute("openid");
        StdDTO dto = new StdDTO();
        List<Map<String, Object>> list = service.queryList(openid);
        if (list != null) {
            dto.setStatus(1);
            dto.setData(list);
        } else {
            dto.setStatus(0);
            dto.setData(null);
        }
        return dto;
    }
    /**
     * 保存体检报告
     * @Description: 
     * @author 吴琪
     * @param model
     * @return 
     * @date: 2017年9月5日 下午2:20:53
     */
    @RequestMapping("/userInfo.action")
    @ResponseBody
    public Object userInfo(HttpServletRequest request) {
        String openid = (String) request.getSession().getAttribute("openid");
        StdDTO dto = new StdDTO();
        Map<String, Object> info = service.getUserByOpenId(openid);
        if (info != null) {
            dto.setStatus(1);
            dto.setData(info);
        } else {
            dto.setStatus(0);
            dto.setData(null);
        }
        return dto;
    }
    
    /**
     * 保存体检报告
     * @Description: 
     * @author 吴琪
     * @param model
     * @return 
     * @date: 2017年9月5日 下午2:20:53
     */
    @RequestMapping("/saveCheck.action")
    @ResponseBody
    public Object saveCheck(CheckReport model) {
        StdDTO dto = new StdDTO();
        Long id = service.saveCheck(model);
        if (id != null) {
            dto.setStatus(1);
            dto.setData(id);
        } else {
            dto.setStatus(0);
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
        StdDTO dto = new StdDTO();
        Long id = service.saveHospital(model);
        if (id != null) {
            dto.setStatus(1);
            dto.setData(id);
        } else {
            dto.setStatus(0);
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
    @RequestMapping("/saveExamine.action")
    @ResponseBody
    public Object saveClinic(ClinicReport model) {
        StdDTO dto = new StdDTO();
        Long id = service.saveExamine(model);
        if (id != null) {
            dto.setStatus(1);
            dto.setData(id);
        } else {
            dto.setStatus(0);
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
        StdDTO dto = new StdDTO();
        Long id = service.saveMedical(model);
        if (id != null) {
            dto.setStatus(1);
            dto.setData(id);
        } else {
            dto.setStatus(0);
            dto.setData(null);
        }
        return dto;
    }
    
    /**
     * 医院列表信息
     * @Description: 
     * @author 吴琪
     * @return 
     * @date: 2017年9月6日 上午9:46:21
     */
    @RequestMapping("/hospitalInfo.action")
    @ResponseBody
    public Object getHospitalList(){
        StdDTO dto = new StdDTO();
        List<Map<String, Object>> data = service.getHospitalList();
        if (data != null) {
            dto.setStatus(1);
            dto.setData(data);
        } else {
            dto.setStatus(0);
            dto.setData(null);
        }
        return dto;
    }
    
    /**
     * 加载科室列表
     * @Description: 
     * @author 吴琪
     * @param hosp_order_id
     * @return 
     * @date: 2017年9月6日 上午11:19:09
     */
    @RequestMapping("/departmentInfo.action")
    @ResponseBody
    public Object getDepartmentList(String hosp_order_id) {
        StdDTO dto = new StdDTO();
        List<Map<String, Object>> data = service.getDepartmentList(hosp_order_id);
        if (data != null) {
            dto.setStatus(1);
            dto.setData(data);
        } else {
            dto.setStatus(0);
            dto.setData(null);
        }
        return dto;
    }
    /**
     * 加载医生
     * @Description: 
     * @author 吴琪
     * @param department_order_id
     * @return 
     * @date: 2017年9月6日 上午11:47:19
     */
    @RequestMapping("/doctorInfo.action")
    @ResponseBody
    public Object getDoctorList(String department_order_id) {
        StdDTO dto = new StdDTO();
        List<Map<String, Object>> data = service.getDoctorList(department_order_id);
        if (data != null) {
            dto.setStatus(1);
            dto.setData(data);
        } else {
            dto.setStatus(0);
            dto.setData(null);
        }
        return dto;
    }
    
    /**
     * 查看门诊报告
     * @Description: 
     * @author 吴琪
     * @param department_order_id
     * @return 
     * @date: 2017年9月6日 上午11:47:19
     */
    @RequestMapping("/examineInfo.action")
    @ResponseBody
    public Object getExamineById(String order_id) {
        StdDTO dto = new StdDTO();
        Map<String, Object> data = service.getExamineById(order_id);
        if (data != null) {
            dto.setStatus(1);
            dto.setData(data);
        } else {
            dto.setStatus(0);
            dto.setData(null);
        }
        return dto;
    }
    
    /**
     * 查看住院报告
     * @Description: 
     * @author 吴琪
     * @param department_order_id
     * @return 
     * @date: 2017年9月6日 上午11:47:19
     */
    @RequestMapping("/hospitalRecordInfo.action")
    @ResponseBody
    public Object getHospitalById(String order_id) {
        StdDTO dto = new StdDTO();
        Map<String, Object> data = service.getHospitalById(order_id);
        if (data != null) {
            dto.setStatus(1);
            dto.setData(data);
        } else {
            dto.setStatus(0);
            dto.setData(null);
        }
        return dto;
    }
    
    /**
     * 查看住院报告
     * @Description: 
     * @author 吴琪
     * @param department_order_id
     * @return 
     * @date: 2017年9月6日 上午11:47:19
     */
    @RequestMapping("/medicalInfo.action")
    @ResponseBody
    public Object getMedicalById(String order_id) {
        StdDTO dto = new StdDTO();
        Map<String, Object> data = service.getMedicalById(order_id);
        if (data != null) {
            dto.setStatus(1);
            dto.setData(data);
        } else {
            dto.setStatus(0);
            dto.setData(null);
        }
        return dto;
    }
    
    /**
     * 查看体检报告
     * @Description: 
     * @author 吴琪
     * @param department_order_id
     * @return 
     * @date: 2017年9月6日 上午11:47:19
     */
    @RequestMapping("/checkInfo.action")
    @ResponseBody
    public Object getCheckById(String order_id) {
        StdDTO dto = new StdDTO();
        Map<String, Object> data = service.getCheckById(order_id);
        if (data != null) {
            dto.setStatus(1);
            dto.setData(data);
        } else {
            dto.setStatus(0);
            dto.setData(null);
        }
        return dto;
    }
    /**
     * 文件上传
     * @Description: 
     * @author 吴琪
     * @param request
     * @param response
     * @return
     * @throws IllegalStateException
     * @throws IOException 
     * @date: 2017年9月6日 上午11:47:31
     */
    @RequestMapping("/upload.action")
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
                // 保存图片
                service.savePicture(request, path);
            }
        }
        return "success";
    }

    private String getFilePath(HttpServletRequest request) {
        String openid = request.getSession().getAttribute("openid").toString();
        // 用户ID
        Map<String, Object> map = service.getUserByOpenId(openid);
        String user_id = null;
        if(null != map){
            user_id = map.get("user_id").toString();
        }
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
}
