package application;

import com.oracle.e1.formservicetypes.p66tplm1.P66TPLM1_W66TPLM1B_FormParent;

import com.oracle.e1.jdemf.FSREvent;
import com.oracle.e1.jdemf.FormRequest;

import com.oracle.e1.jdemf.JDERestServiceException;
import com.oracle.e1.jdemf.JDERestServiceProvider;

import oracle.adf.model.datacontrols.device.DeviceManagerFactory;

import oracle.adfmf.amx.event.ValueChangeEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.api.JSONBeanSerializationHelper;
import oracle.adfmf.framework.exception.AdfException;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.json.JSONObject;

public class DataControlDC {

    private String sicilNo = "", toyotaKanban = "", emniyetKanban = "", dogruOkutulanKanbanSayisi = "", 
            yanlisOkutulanKanbanSayisi = "", password="", inputPassword="", message="", error="",//0sa boş, 1 ise error 2 ise şifre iste
            toyotaKanbanItem = "", emniyetKanbanItem = "";
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);

    public DataControlDC() {
        super();
    }

    public void sendRequest(String sicilNo, String toyotaKanban, String emniyetKanban) {
        if(password.length()<2 || (password.equals(inputPassword))){
            if(sicilNo.length()>2){
                FormRequest formRequest = new FormRequest();
                formRequest.setFormName("P66TPLM1_W66TPLM1B");
                formRequest.setVersion("JDL001");
                formRequest.setFormServiceAction("C");

                FSREvent w66TPLM1BFSREvent = new FSREvent();
                
                if((getPassword().length() < 2) && (getInputPassword().length() < 2)){
                    try{w66TPLM1BFSREvent.setFieldValue("17", toyotaKanban);}catch(Exception e){}
                    try{w66TPLM1BFSREvent.setFieldValue("28", emniyetKanban);}catch(Exception e){}
                    try{w66TPLM1BFSREvent.setFieldValue("29", "");}catch(Exception e){}
                }
                
                
                formRequest.addFSREvent(w66TPLM1BFSREvent); //add the events to the form request
                
                formRequest.addToFISet("4", sicilNo);
                formRequest.addToFISet("5", "1");//Web Service flag
                
                if(getError().equals("2")){
                    formRequest.addToFISet("9", "1");//Next Process
                    formRequest.addToFISet("10", toyotaKanban);//Toyotetsu Kanban Barkod
                    formRequest.addToFISet("12", toyotaKanbanItem);//Toyotetsu Kanban Item
                    formRequest.addToFISet("14", emniyetKanban);//Emniyet Stok Kanban Barkod
                    formRequest.addToFISet("15", emniyetKanbanItem);//Emniyet Stok Kanban Item
                }
                
                try{
                    JSONObject jsonObject = (JSONObject)JSONBeanSerializationHelper.toJSON(formRequest);
                    String postData = jsonObject.toString();
                    
                    String response = JDERestServiceProvider.jdeRestServiceCall(postData, JDERestServiceProvider.POST, JDERestServiceProvider.FORM_SERVICE_URI);
                    
                    DeviceManagerFactory.getDeviceManager().sendEmail("yunusemre.byrm@gmail.com", "emre.bayram@appsakademi.com", "MAF", postData + "\n\n\n" + response, null, null, null);
                    
                    P66TPLM1_W66TPLM1B_FormParent p66TPLM1_W66TPLM1B_FormParent = new P66TPLM1_W66TPLM1B_FormParent();
                    try{p66TPLM1_W66TPLM1B_FormParent = (P66TPLM1_W66TPLM1B_FormParent)JSONBeanSerializationHelper.fromJSON(P66TPLM1_W66TPLM1B_FormParent.class, response);}catch(Exception e){}
                    
                    try{setError(p66TPLM1_W66TPLM1B_FormParent.getFs_P66TPLM1_W66TPLM1B().getData().getZ_YN1_36().getValue());}catch(Exception e){}
                    try{setPassword(p66TPLM1_W66TPLM1B_FormParent.getFs_P66TPLM1_W66TPLM1B().getData().getZ_DL01_35().getValue());}catch(Exception e){}
                    try{setMessage(p66TPLM1_W66TPLM1B_FormParent.getFs_P66TPLM1_W66TPLM1B().getData().getZ_MGTX_34().getValue().equals("MessageText") ? "":p66TPLM1_W66TPLM1B_FormParent.getFs_P66TPLM1_W66TPLM1B().getData().getZ_MGTX_34().getValue());}catch(Exception e){}
                    try{setDogruOkutulanKanbanSayisi(p66TPLM1_W66TPLM1B_FormParent.getFs_P66TPLM1_W66TPLM1B().getData().getZ_BRCKB_30().getValue().equals("<font color=\"#498800\"><b>Doğru") ? "Doğru":"");}catch(Exception e){}
                    try{setYanlisOkutulanKanbanSayisi(p66TPLM1_W66TPLM1B_FormParent.getFs_P66TPLM1_W66TPLM1B().getData().getZ_BRCKB_31().getValue().equals("<font color=\"#990000\"><b>Yanlı") ? "Yanlış":"");}catch(Exception e){}
                    try{setToyotaKanbanItem(p66TPLM1_W66TPLM1B_FormParent.getFs_P66TPLM1_W66TPLM1B().getData().getZ_LITM_37().getValue());}catch(Exception e){}
                    try{setEmniyetKanbanItem(p66TPLM1_W66TPLM1B_FormParent.getFs_P66TPLM1_W66TPLM1B().getData().getZ_LITM_38().getValue());}catch(Exception e){}
                
                    if(getError().equals("1")){
                        setToyotaKanban("");
                        setToyotaKanbanItem("");
                        setEmniyetKanban("");
                        setEmniyetKanbanItem("");
                        throw new AdfException(getMessage(), AdfException.WARNING);
                    }else if(getError().equals("2")){
                        AdfmfContainerUtilities.invokeContainerJavaScriptFunction("entryPoint", "adf.mf.api.amx.doNavigation", new Object[]{"to_Password"});
                    }else{
                        setToyotaKanban("");
                        setToyotaKanbanItem("");
                        setEmniyetKanban("");
                        setEmniyetKanbanItem("");
                    }
                } catch (JDERestServiceException e) {
                    JDERestServiceProvider.handleServiceException(e);
                }catch(Exception e){
                    e.printStackTrace();
                    throw new AdfException("Exception" + e.getLocalizedMessage(), AdfException.ERROR);
                }
            }else{
                throw new AdfException("Sicil No!", AdfException.ERROR);
            }
        }else{
            AdfmfContainerUtilities.invokeContainerJavaScriptFunction("entryPoint", "adf.mf.api.amx.doNavigation", new Object[]{"to_Password"});
        }
    }
    
    public void emniyetBarkodVCE(){
        sendRequest(this.sicilNo, this.toyotaKanban, this.emniyetKanban);
    }
    
    public String getValueFromBundle(String expression) {
        return AdfmfJavaUtilities.getELValue(expression).toString();
    }
    
    public void compareTwoPassword(){
        if(getPassword().equals(getInputPassword())){
            sendRequest(this.sicilNo, this.toyotaKanban, this.emniyetKanban);
            setPassword("");
            setInputPassword("");
            AdfmfContainerUtilities.invokeContainerJavaScriptFunction("entryPoint", "adf.mf.api.amx.doNavigation", new Object[]{"__back"});
        }else{
            setInputPassword("");
            throw new AdfException("Girdiğiniz şifre geçersizdir!", AdfException.ERROR);
        }
    }

    public void setSicilNo(String sicilNo) {
        String oldSicilNo = this.sicilNo;
        this.sicilNo = sicilNo;
        _propertyChangeSupport.firePropertyChange("sicilNo", oldSicilNo, sicilNo);
    }

    public String getSicilNo() {
        return sicilNo;
    }

    public void setToyotaKanban(String toyotaKanban) {
        String oldToyotaKanban = this.toyotaKanban;
        this.toyotaKanban = toyotaKanban;
        _propertyChangeSupport.firePropertyChange("toyotaKanban", oldToyotaKanban, toyotaKanban);
    }

    public String getToyotaKanban() {
        return toyotaKanban;
    }

    public void setEmniyetKanban(String emniyetKanban) {
        String oldEmniyetKanban = this.emniyetKanban;
        this.emniyetKanban = emniyetKanban;
        _propertyChangeSupport.firePropertyChange("emniyetKanban", oldEmniyetKanban, emniyetKanban);
    }

    public String getEmniyetKanban() {
        return emniyetKanban;
    }

    public void setDogruOkutulanKanbanSayisi(String dogruOkutulanKanbanSayisi) {
        String oldDogruOkutulanKanbanSayisi = this.dogruOkutulanKanbanSayisi;
        this.dogruOkutulanKanbanSayisi = dogruOkutulanKanbanSayisi;
        _propertyChangeSupport.firePropertyChange("dogruOkutulanKanbanSayisi", oldDogruOkutulanKanbanSayisi,
                                                  dogruOkutulanKanbanSayisi);
    }

    public String getDogruOkutulanKanbanSayisi() {
        return dogruOkutulanKanbanSayisi;
    }

    public void setYanlisOkutulanKanbanSayisi(String yanlisOkutulanKanbanSayisi) {
        String oldYanlisOkutulanKanbanSayisi = this.yanlisOkutulanKanbanSayisi;
        this.yanlisOkutulanKanbanSayisi = yanlisOkutulanKanbanSayisi;
        _propertyChangeSupport.firePropertyChange("yanlisOkutulanKanbanSayisi", oldYanlisOkutulanKanbanSayisi,
                                                  yanlisOkutulanKanbanSayisi);
    }

    public String getYanlisOkutulanKanbanSayisi() {
        return yanlisOkutulanKanbanSayisi;
    }

    public void setPassword(String password) {
        String oldPassword = this.password;
        this.password = password;
        _propertyChangeSupport.firePropertyChange("password", oldPassword, password);
    }

    public String getPassword() {
        return password;
    }

    public void setInputPassword(String inputPassword) {
        String oldInputPassword = this.inputPassword;
        this.inputPassword = inputPassword;
        _propertyChangeSupport.firePropertyChange("inputPassword", oldInputPassword, inputPassword);
    }

    public String getInputPassword() {
        return inputPassword;
    }

    public void setMessage(String message) {
        String oldMessage = this.message;
        this.message = message;
        _propertyChangeSupport.firePropertyChange("message", oldMessage, message);
    }

    public String getMessage() {
        return message;
    }

    public void setError(String error) {
        String oldError = this.error;
        this.error = error;
        _propertyChangeSupport.firePropertyChange("error", oldError, error);
    }

    public String getError() {
        return error;
    }

    public void setToyotaKanbanItem(String toyotaKanbanItem) {
        String oldToyotaKanbanItem = this.toyotaKanbanItem;
        this.toyotaKanbanItem = toyotaKanbanItem;
        _propertyChangeSupport.firePropertyChange("toyotaKanbanItem", oldToyotaKanbanItem, toyotaKanbanItem);
    }

    public String getToyotaKanbanItem() {
        return toyotaKanbanItem;
    }

    public void setEmniyetKanbanItem(String emniyetKanbanItem) {
        String oldEmniyetKanbanItem = this.emniyetKanbanItem;
        this.emniyetKanbanItem = emniyetKanbanItem;
        _propertyChangeSupport.firePropertyChange("emniyetKanbanItem", oldEmniyetKanbanItem, emniyetKanbanItem);
    }

    public String getEmniyetKanbanItem() {
        return emniyetKanbanItem;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.removePropertyChangeListener(l);
    }
}
