package turfcuttinggui;


//Column Names
// FULL_NAME	    ADDRESS	                 CITY STATE	 ZIP_CODE	        HOME_PHONE	CELL_PHONE
// EMAIL            WORK_EMAIL	             HIRE_DATE	 ANNIVERSARY_DATE	DEPT_NAME	LOCATION_DESCRIPTION
// JOB_DESCRIPTION  Anniversary_date	     Senior_Date
public class Record {
    private int ID;
    private String FULL_NAME;
    private String ADDRESS;
    private String CITY;
    private String STATE;
    private String ZIP_CODE;
    private String HOME_PHONE;
    private String CELL_PHONE;
    private String EMAIL;
    private String WORK_EMAIL;
    private String HIRE_DATE;
    private String ANNIVERSARY_DATE;
    private String DEPT_NAME;
    private String LOCATION_DESCRIPTION;
    private String JOB_DESCRIPTION;
    private String Anniversary_date;
    private String Senior_Date;


    public Record(int ID, String FULL_NAME, String ADDRESS, String CITY, String STATE, String ZIP_CODE, String HOME_PHONE,
                  String CELL_PHONE, String EMAIL, String WORK_EMAIL, String HIRE_DATE, String ANNIVERSARY_DATE,
                  String DEPT_NAME, String LOCATION_DESCRIPTION, String JOB_DESCRIPTION,
                  String anniversary_date, String senior_Date) {
        this.ID = ID;
        this.FULL_NAME = FULL_NAME;
        this.ADDRESS = ADDRESS;
        this.CITY = CITY;
        this.STATE = STATE;
        this.ZIP_CODE = ZIP_CODE;
        this.HOME_PHONE = HOME_PHONE;
        this.CELL_PHONE = CELL_PHONE;
        this.EMAIL = EMAIL;
        this.WORK_EMAIL = WORK_EMAIL;
        this.HIRE_DATE = HIRE_DATE;
        this.ANNIVERSARY_DATE = ANNIVERSARY_DATE;
        this.DEPT_NAME = DEPT_NAME;
        this.LOCATION_DESCRIPTION = LOCATION_DESCRIPTION;
        this.JOB_DESCRIPTION = JOB_DESCRIPTION;
        this.Anniversary_date = anniversary_date;
        this.Senior_Date = senior_Date;
    }

    public int getID() {
        return ID;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public String getANNIVERSARY_DATE() {
        return ANNIVERSARY_DATE;
    }

    public String getCELL_PHONE() {
        return CELL_PHONE;
    }

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public String getCITY() {
        return CITY;
    }

    public String getSTATE() {
        return STATE;
    }

    public String getZIP_CODE() {
        return ZIP_CODE;
    }

    public String getHOME_PHONE() {
        return HOME_PHONE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getWORK_EMAIL() {
        return WORK_EMAIL;
    }

    public String getHIRE_DATE() {
        return HIRE_DATE;
    }

    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    public String getLOCATION_DESCRIPTION() {
        return LOCATION_DESCRIPTION;
    }

    public String getJOB_DESCRIPTION() {
        return JOB_DESCRIPTION;
    }

    public String getAnniversary_date() {
        return Anniversary_date;
    }

    public String getSenior_Date() {
        return Senior_Date;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setFULL_NAME(String FULL_NAME) {
        this.FULL_NAME = FULL_NAME;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public void setZIP_CODE(String ZIP_CODE) {
        this.ZIP_CODE = ZIP_CODE;
    }

    public void setHOME_PHONE(String HOME_PHONE) {
        this.HOME_PHONE = HOME_PHONE;
    }

    public void setCELL_PHONE(String CELL_PHONE) {
        this.CELL_PHONE = CELL_PHONE;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public void setWORK_EMAIL(String WORK_EMAIL) {
        this.WORK_EMAIL = WORK_EMAIL;
    }

    public void setHIRE_DATE(String HIRE_DATE) {
        this.HIRE_DATE = HIRE_DATE;
    }

    public void setANNIVERSARY_DATE(String ANNIVERSARY_DATE) {
        this.ANNIVERSARY_DATE = ANNIVERSARY_DATE;
    }

    public void setDEPT_NAME(String DEPT_NAME) {
        this.DEPT_NAME = DEPT_NAME;
    }

    public void setLOCATION_DESCRIPTION(String LOCATION_DESCRIPTION) {
        this.LOCATION_DESCRIPTION = LOCATION_DESCRIPTION;
    }

    public void setJOB_DESCRIPTION(String JOB_DESCRIPTION) {
        this.JOB_DESCRIPTION = JOB_DESCRIPTION;
    }

    public void setAnniversary_date(String anniversary_date) {
        Anniversary_date = anniversary_date;
    }

    public void setSenior_Date(String senior_Date) {
        Senior_Date = senior_Date;
    }
}
