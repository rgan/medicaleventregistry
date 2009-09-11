package org.healthapps.medicaleventregistry {
import flexunit.framework.TestCase;

public class ValidateHelperTest extends TestCase {

    public function testShouldValidateLoginInputs():void {
        var validateHelper:ValidateHelper = new ValidateHelper();
        var msg:String = validateHelper.validateLoginInputs('foo', 'bar');
        assertTrue(msg == '');
        msg = validateHelper.validateLoginInputs('', 'bar');
        assertFalse(msg == '');
        msg = validateHelper.validateLoginInputs('foo', '');
        assertFalse(msg == '');
    }

}
}