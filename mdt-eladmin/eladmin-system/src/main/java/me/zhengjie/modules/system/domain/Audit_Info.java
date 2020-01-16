package me.zhengjie.modules.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Audit_Info {

	private Integer pathway_id;
	private String pathway_name;
	private String submitter;
	private String data_upload_moment;
	private String checker;
	private Integer audit_state;

	private Date audit_time;
	private String audit_remark;
	private Integer submitterid;
	private Integer checkerid;

	public Audit_Info() {
		super();
	}
	public Audit_Info(Integer pathway_id, String pathway_name, String submitter, String data_upload_moment, String checker, Integer audit_state, Date audit_time, String audit_remark, Integer submitterid, Integer checkerid) {
		this.pathway_id = pathway_id;
		this.pathway_name = pathway_name;
		this.submitter = submitter;
		this.data_upload_moment = data_upload_moment;
		this.checker = checker;
		this.audit_state = audit_state;
		this.audit_time = audit_time;
		this.audit_remark = audit_remark;
		this.submitterid = submitterid;
		this.checkerid = checkerid;
	}

	public Integer getPathway_id() {
		return pathway_id;
	}

	public void setPathway_id(Integer pathway_id) {
		this.pathway_id = pathway_id;
	}

	public String getPathway_name() {
		return pathway_name;
	}

	public void setPathway_name(String pathway_name) {
		this.pathway_name = pathway_name;
	}

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public String getData_upload_moment() {
		return data_upload_moment;
	}

	public void setData_upload_moment(String data_upload_moment) {
		this.data_upload_moment = data_upload_moment;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public Integer getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(Integer audit_state) {
		this.audit_state = audit_state;
	}


	public Date getAudit_time() {
		return audit_time;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public void setAudit_time(Date audit_time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.CHINA);
		Date data = null;
		try {
			//System.out.println(simpleDateFormat.format(audit_time));
			data = simpleDateFormat.parse(simpleDateFormat.format(audit_time));
//			System.out.println(data.toString());
			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	//System.out.println(simpleDateFormat2.format(data));
			System.out.println(data.toString());
		}catch (Exception e){
			e.printStackTrace();
		}
		this.audit_time = data;
	}

	public String getAudit_remark() {
		return audit_remark;
	}

	public void setAudit_remark(String audit_remark) {
		this.audit_remark = audit_remark;
	}

	public Integer getSubmitterid() {
		return submitterid;
	}

	public void setSubmitterid(Integer submitterid) {
		this.submitterid = submitterid;
	}

	public Integer getCheckerid() {
		return checkerid;
	}

	public void setCheckerid(Integer checkerid) {
		this.checkerid = checkerid;
	}
}
