package testcases;

import org.junit.Test;
import org.junit.runner.RunWith;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import steps.USSteps;


	@RunWith(SerenityRunner.class)
	public class UserStory6Test extends BaseTest {
		

		@Steps
		USSteps api;
		
		@Title("Validate Voucher Insight")
		@Test
		public void validateVoucherInsight() {
			api.validateVoucherInsightAPIResponse();
		}
	}