package com.haaa.cloudmedical.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.haaa.cloudmedical.common.dao.BaseTemplateDao;
import com.haaa.cloudmedical.common.entity.Page;
import com.haaa.cloudmedical.common.util.DateUtil;
import com.haaa.cloudmedical.common.util.IdCard;
import com.haaa.cloudmedical.common.util.StringUtil;
import com.haaa.cloudmedical.entity.Hypertension;
import com.haaa.cloudmedical.entity.HypertensionAnswer;
import com.haaa.cloudmedical.entity.HypertensionQuestion;
@Repository
public class ChronicRecordsDao extends BaseTemplateDao{}
