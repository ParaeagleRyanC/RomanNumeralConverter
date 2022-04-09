import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NumeralConverter {

    public static void main(String[] args) {

        String inputToConvert;
        String romanNumerals;
        int decimalNumber;

        System.out.println("Hit ENTER to EXIT.");

        do {
            System.out.print("Enter either a roman numeral or a decimal number: ");
            inputToConvert = getInput();
            // exit when no input is detected
            if (inputToConvert.equals("")) {
                break;
            }

            // only pure decimals or pure roman numerals are allowed.
            try {
                // decimal to roman
                Integer.parseInt(inputToConvert);
                romanNumerals = decimalToRoman(inputToConvert);
                System.out.printf("%s is %s.\n", inputToConvert, romanNumerals);
            } catch (Exception e) {
                if (!isRomanNumeralsValid(inputToConvert.toUpperCase())) {
                    System.out.println("Invalid input.");
                    continue;
                }
                // roman to decimal
                decimalNumber = romanToDecimal(inputToConvert.toUpperCase());
                System.out.printf("%s is %d.\n", inputToConvert, decimalNumber);

            }
        } while (true);
    }

    // get input method
    private static String getInput() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    // check for valid roman numeral input
    private static boolean isRomanNumeralsValid(String input) {

        List<Character> greaterThanV = new ArrayList<>(List.of('X', 'L', 'C', 'D', 'M'));

        // only one V and L and D is allowed
        int vCount = 0;
        int lCount = 0;
        int dCount = 0;
        for (int index = 0; index < input.length(); index++) {
            if (vCount > 1 || lCount > 1 || dCount > 1) {
                return false;
            }
            if (input.charAt(index) == 'V') {
                vCount++;
            }
            if (input.charAt(index) == 'L') {
                lCount++;
            }
            if (input.charAt(index) == 'D') {
                dCount++;
            }
        }

        // validation for V
        int vPosition = input.indexOf('V');
        if (vPosition > 0) {
            for (int index = vPosition + 1; index < input.length(); index++) {
                if (greaterThanV.contains(input.charAt(index))) {
                    return false;
                }
            }
        }

        // validation for I
        int iCount = 0;
        for (int index = 0; index < input.length(); index++) {
            char firstNumeral = input.charAt(index);
            char secondNumeral = 0;
            if (index < input.length() - 1) {
                secondNumeral = input.charAt(index + 1);
            }

            if (firstNumeral == 'I') {
                iCount++;
                if (secondNumeral == 'X' && iCount > 1) {
                    return false;
                }
                else if (secondNumeral == 'X' && index == input.length() - 2) {
                    return true;
                }
                else if (greaterThanV.contains(secondNumeral)) {
                    return false;
                }
            }

        }
        return true;
    }

    // decimal to roman
    private static String decimalToRoman(String number) {
        List<Character> listOfNumbers = new ArrayList<>();
        StringBuilder romanNumeral = new StringBuilder();

        // store number as a list of numbers
        for (int index = 0; index < number.length(); index++) {
            listOfNumbers.add(number.charAt(index));
        }

        // iterate through the numbers
        for (int index = 0; index < listOfNumbers.size(); index++) {
            if (listOfNumbers.size() - index == 4) {
                romanNumeral.append(assignThousands(Integer.parseInt(listOfNumbers.get(index).toString())));
            }

            else if (listOfNumbers.size() - index == 3) {
                romanNumeral.append(assignHundreds(Integer.parseInt(listOfNumbers.get(index).toString())));
            }

            else if (listOfNumbers.size() - index == 2) {
                romanNumeral.append(assignTens(Integer.parseInt(listOfNumbers.get(index).toString())));
            }

            else if (listOfNumbers.size() - index == 1) {
                romanNumeral.append(assignOnes(Integer.parseInt(listOfNumbers.get(index).toString())));

            }
        }
        return romanNumeral.toString();
    }

    private static String assignThousands(int number) {
        StringBuilder mS = new StringBuilder();
        for (int i = 0; i < number; i++) {
            mS.append("M");
        }
        return mS.toString();
    }

    private static String assignHundreds(int number) {
        switch (number) {
            case 1: return "C";
            case 2 : return "CC";
            case 3 : return "CCC";
            case 4 : return "CD";
            case 5 : return "D";
            case 6 : return "DC";
            case 7 : return "DCC";
            case 8 : return "DCCC";
            case 9 : return "CM";
            default : return "";
        }
    }

    private static String assignTens(int number) {
        switch (number) {
            case 1 : return "X";
            case 2 : return "XX";
            case 3 : return "XXX";
            case 4 : return "XL";
            case 5 : return "L";
            case 6 : return "LX";
            case 7 : return "LXX";
            case 8 : return "LXXX";
            case 9 : return "XC";
            default : return "";
        }
    }

    private static String assignOnes(int number) {
        switch (number) {
            case 1 : return "I";
            case 2 : return "II";
            case 3 : return "III";
            case 4 : return "IV";
            case 5 : return "V";
            case 6 : return "VI";
            case 7 : return "VII";
            case 8 : return "VIII";
            case 9 : return "IX";
            default : return "";
        }
    }

    // roman to decimal
    private static int romanToDecimal(String number) {

        boolean isAdvanceToNext = false;
        int decimalSum = 0;

        // iterate through the roman numerals
        for (int index = 0; index < number.length(); index++) {

            // advance to next numeral if flag is true
            if (isAdvanceToNext) {
                isAdvanceToNext = false;
                continue;
            }

            // assign numbers to compare
            int firstNumeral = assignRomanToDecimal(number.charAt(index));
            int secondNumeral = 0;
            if (index < number.length() - 1) {
                secondNumeral = assignRomanToDecimal(number.charAt(index + 1));
            }

            // if the first symbol the second symbol immediately following it
            // the first symbol is subtracted from the second symbol
            if (firstNumeral < secondNumeral) {
                decimalSum += (secondNumeral - firstNumeral);
                isAdvanceToNext = true;
            }

            else {
                if (index == number.length() - 1) {
                    decimalSum += (secondNumeral + firstNumeral);
                }
                else {
                    decimalSum += firstNumeral;
                }
            }
        }

        return decimalSum;
    }

    private static int assignRomanToDecimal(Character roman) {
        switch (roman) {
            case 'I' : return 1;
            case 'V' : return 5;
            case 'X' : return 10;
            case 'L' : return 50;
            case 'C' : return 100;
            case 'D' : return 500;
            case 'M' : return 1000;
            default : return 0;
        }
    }
}
