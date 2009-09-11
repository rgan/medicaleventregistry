package org.healthapps.medicaleventregistry {

public class ValidateHelper
{
    public function ValidateHelper() {
    }

    public function validateRegisterInputs(username:String, pwd:String, email:String):String
    {
        if (username == '' || pwd == '' || email=='') {
            return "Make sure all fields have valid values."
        }
        return "";
    }

    public function validateAddEventTypeInputs(typeName:String):String {
        if(typeName == '') {
            return "Type name must not be empty"
        }
        return "";
    }

    public function validateEventInputs(who:String, lat:String, lon:String, date:String, selectedTypeIndex:int):String
    {
        if (who == '' || lat == '' || lon == '' || date == '' || selectedTypeIndex == -1) {
            return "Make sure all fields have valid values."
        }
        return "";
    }

    public function validateLoginInputs(login:String, pwd:String):String {
        if (login == '' || pwd == '') {
            return "Both username and password must be provided"
        }
        return '';
    }

    public function validateSearchInputs(dateFrom:String, dateTo:String, selectedTypeIndex:int):String
    {
        if (dateFrom == '' || dateTo == '' || selectedTypeIndex == -1) {
            return "Make sure all fields have valid values."
        }
        return "";
    }
}
}