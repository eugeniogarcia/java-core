package com.baeldung.java14.npe;


import static com.baeldung.java14.npe.HelpfulNullPointerException.Employee;
import static com.baeldung.java14.npe.HelpfulNullPointerException.PersonalDetails;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baeldung.java14.record.Person;

public class HelpfulNullPointerExceptionUnitTest {

    @Test
    public void givenAnEmptyPersonalDetails_whenEmailAddressIsAccessed_thenThrowNPE() {
   	 Assertions.assertThrows(NullPointerException.class, () -> {
         var helpfulNPE = new HelpfulNullPointerException();

         var employee = new Employee();
         employee.setName("Eduard");
         employee.setPersonalDetails(new PersonalDetails());
         helpfulNPE.getEmployeeEmailAddress(employee);
		  });
   	 
    }

    @Test
    public void givenCompletePersonalDetails_whenEmailAddressIsAccessed_thenSuccess() {
        var helpfulNPE = new HelpfulNullPointerException();
        var emailAddress = "eduard@gmx.com";

        var employee = new Employee();
        employee.setName("Eduard");

        var personalDetails = new PersonalDetails();
        personalDetails.setEmailAddress(emailAddress.toUpperCase());
        personalDetails.setPhone("1234");
        employee.setPersonalDetails(personalDetails);

        assertThat(helpfulNPE.getEmployeeEmailAddress(employee)).isEqualTo(emailAddress);
    }

}
