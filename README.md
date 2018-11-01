# airwellexdemo
## USAGE
demo steps:

* copy settings.xml to ${maven}/conf

* run command  mvn test


## BUGS

* payment method是LOCAL时，错把bank_country_code为CN当成US解析。
    * request
        ~~~JSON
         {
                "aba": "",
                "account_name": "gangtiexia",
                "account_number": "qwertyuiopasdfghjklm",
                "bank_country_code": "CN",
                "bsb": "",
                "payment_method": "LOCAL",
                "swift_code": ""
               }
         ~~~
    * response
        ~~~JSON
        {
            "error": "Length of account_number should be between 7 and 11 when bank_country_code is 'US'"
        }
        ~~~
    
    * expected
        ~~~JSON
        {
            "success": "Bank details saved"
        }
        ~~~
    

* bank country code 是US，对account number长短判断有问题。
    * request
        ~~~JSON
         {
         	"aba": "123456789",
         	"account_name": "钢铁aa侠钢铁侠侠侠",
         	"account_number": "1234567890@#$%^&*(",
         	"bank_country_code": "US",
         	"bsb": "",
         	"payment_method": "LOCAL",
         	"swift_code": ""
         }
         ~~~
    * response
        ~~~JSON
        {
        	"error": "Length of account_number should be between 7 and 11 when bank_country_code is 'US'"
        }
        ~~~
    
    * expected
        ~~~JSON
        {
        	"error": "Length of account_number should be between 1 and 17 when bank_country_code is 'US'"
        }
        ~~~

* 错把bank_country_code 为AU当成US解析
    * request
        ~~~JSON
         {
         	"aba": "",
         	"account_name": "钢铁acv%^18",
         	"account_number": "12345",
         	"bank_country_code": "AU",
         	"bsb": "asdfgh",
         	"payment_method": "SWIFT",
         	"swift_code": "!@#$AU23_*"
         }
         ~~~
    * response
        ~~~JSON
        {
        	"error": "Length of account_number should be between 7 and 11 when bank_country_code is 'US'"
        }
        ~~~
    
    * expected
        ~~~JSON
        {
        	"error": "Length of account_number should be between 6 and 9 when bank_country_code is 'AU'"
        }
        ~~~
 
* bank country code =CN & account_number < 8 ,对account number的判断无效
    * request
        ~~~JSON
         {
         	"aba": "",
         	"account_name": "gt",
         	"account_number": "sdfzxc",
         	"bank_country_code": "CN",
         	"bsb": "",
         	"payment_method": "SWIFT",
         	"swift_code": "asdgCN12"
         }
         ~~~
    * response
        ~~~JSON
        {
        	"success": "Bank details saved"
        }
        ~~~
    
    * expected
        ~~~JSON
        {
        	"error": "Length of account_number should be between 8 and 20 when bank_country_code is 'CN'"
        }
        ~~~

* bank country code =CN & account_number > 20 ,使用了US的规则判断account number
    * request
        ~~~JSON
        {
        	"aba": "",
        	"account_name": "gangtiexia",
        	"account_number": "qwertyuiopasdfghjkl（（（（（（m",
        	"bank_country_code": "CN",
        	"bsb": "",
        	"payment_method": "SWIFT",
        	"swift_code": "adfgCN12"
        }
         ~~~
    * response
        ~~~JSON
        {
        	"error": "Length of account_number should be between 7 and 11 when bank_country_code is 'US'"
        }
        ~~~
    
    * expected
        ~~~JSON
        {
        	"error": "Length of account_number should be between 8 and 20 when bank_country_code is 'CN'"
        }
        ~~~
        
        
* bank country code =US， aba不能为空
    * request
        ~~~JSON
        {
        	"aba": "",
        	"account_name": "钢铁",
        	"account_number": "1234567890",
        	"bank_country_code": "US",
        	"bsb": "",
        	"payment_method": "LOCAL",
        	"swift_code": ""
        }
         ~~~
    * response
        ~~~JSON
        {
        	"success": "Bank details saved"
        }
        ~~~
    
    * expected
        ~~~JSON
        {
        	"error": "'aba' is required when bank country code is 'US'"
        }
        ~~~
