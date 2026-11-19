package lk.lms.lmsystem.validation;

public enum Validation {
    EMAIL_VALIDATION {
        @Override
        public String validate() {
            return "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

    }, MOBILE_VALIDATION {
        @Override
        public String validate() {
            return "^07[01245678]{1}[0-9]{7}$"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

    }, LANDLINE_VALIDATION {
        @Override
        public String validate() {
            return "^(?:0|94|\\+94|0094)?(?:(?P<area>11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(?P<land_carrier>0|2|3|4|5|7|9)|7(?P<mobile_carrier>0|1|2|4|5|6|7|8)\\d)\\d{6}$`"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

    }, PASSWORD_VALIDATION {
        @Override
        public String validate() {
            return "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }
    }, POSTALCODE_VALIDATION {
        @Override
        public String validate() {
            return "^\\d{5}$"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

    }, NICNUMBER_VALIDATION {
        @Override
        public String validate() {
            return "^([0-9]{9}[x|X|v|V]|[0-9]{12})$"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }
    };

    public String validate() {
        return "";
    }
}
