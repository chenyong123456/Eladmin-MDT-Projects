package cn.knowimage.pojo.instance;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// UserInfo默认对应的表名为user_info -----> 表名默认使用类名,驼峰转下划线
@Data
@Table(name = "PATIENT_MESSAGE")
public class PatientMessage {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    // 表字段默认为Java对象的Field名字驼峰转下划线形式 patienName = patient_name
    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "age")
    private Integer age;

    private String sex;

    private String phone;

    @Column(name = "disease_area")
    private String diseaseArea;  // disease_area驼峰转下划线形式

    @Column(name = "disease_number")
    private Integer diseaseNumber;  // disease_number驼峰转下划线形式

    @Column(name = "hospital_number")
    private Integer hospitalNumber;  // hospital_number驼峰转下划线形式

    @Column(name = "doctor_main")
    private String doctorMain;  // doctor_main驼峰转下划线形式

    @Column(name = "mdt_id")
    private Integer mdtId;  // mdt_id驼峰转下划线形式
}
