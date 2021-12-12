package turfcuttinggui;

/* RECORD POSITIONS / COLUMN NAMES
[0]  = ID
[1]  = FULL_NAME
[2]  = NEW_OR_OLD
[2]  = ADDRESS
[3]  = CITY
[4]  = STATE
[5]  = ZIP_CODE
[6]  = HOME_PHONE
[7]  = CELL_PHONE
[8]  = EMAIL
[9]  = WORK_EMAIL
[10] = ADJ_HIRE_DATE
[11] = ANNIVERSARY_DATE
[12] = SENIOR_DATE
[13] = DEPT_NAME
[14] = LOCATION_DESCRIPTION
[15] = POSITION_DESCRIPTION
[16] = RESULTS
 */
public class Record {
    private int ID;//0
    private String FULL_NAME;//1
    private final String NEW_OR_OLD;//2
    private String ADDRESS;//3
    private String CITY;//4
    private String STATE;//5
    private String ZIP_CODE;//6
    private String HOME_PHONE;//7
    private String CELL_PHONE;//8
    private String EMAIL;//9
    private String WORK_EMAIL;//10
    private String HIRE_DATE;//11
    private String ANNIVERSARY_DATE;//12
    private String DEPT_NAME;//13
    private String LOCATION_DESCRIPTION;//14
    private String JOB_DESCRIPTION;//15
    private String Senior_Date;//16
    private final String RESULT;//17


    public Record(int ID, String FULL_NAME, String NEW_OR_OLD, String ADDRESS, String CITY, String STATE, String ZIP_CODE, String HOME_PHONE,
                  String CELL_PHONE, String EMAIL, String WORK_EMAIL, String HIRE_DATE, String ANNIVERSARY_DATE,
                  String DEPT_NAME, String LOCATION_DESCRIPTION, String JOB_DESCRIPTION,
                  String senior_Date, String RESULT) {
        this.ID = ID;
        this.FULL_NAME = FULL_NAME;
        this.NEW_OR_OLD = NEW_OR_OLD;
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
        this.Senior_Date = senior_Date;
        this.RESULT = RESULT;
    }

    public int getID() {
        return ID;
    }

    public String getADDRESS() {
        return ADDRESS;
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

    public String getANNIVERSARY_DATE(){ return ANNIVERSARY_DATE;}

    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    public String getLOCATION_DESCRIPTION() {
        return LOCATION_DESCRIPTION;
    }

    public String getJOB_DESCRIPTION() {
        return JOB_DESCRIPTION;
    }

    public String getSenior_Date() {
        return Senior_Date;
    }
    public String getRESULT(){return RESULT;}
    public String getNEW_OR_OLD(){return NEW_OR_OLD;}

    public String[] getStringArray(){
        String id = String.valueOf(getID());
        String[] aryRecord = {id,getFULL_NAME(),getNEW_OR_OLD(),getADDRESS(),getCITY(),getSTATE(),getZIP_CODE(),
        getHOME_PHONE(),getCELL_PHONE(),getEMAIL(),getWORK_EMAIL(),getHIRE_DATE(),getANNIVERSARY_DATE(),getSenior_Date(),
        getDEPT_NAME(),getLOCATION_DESCRIPTION(),getJOB_DESCRIPTION(),getRESULT()};

        return aryRecord;
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

    public void setSenior_Date(String senior_Date) {
        Senior_Date = senior_Date;
    }
}
