import java.util.Scanner;
import java.util.Arrays;

public class Main {
    static String[] rUnits = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
    static boolean roman = false;
    static int inputMin = 1;
    static int inputMax = 10;

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Введите выражение");
        String input = scn.nextLine();
        System.out.println(calc(input));
    }

    //Калькулятор
    public static String calc(String input) {

        String[] operators = {"+", "-", "/", "*"};
        String[] sOperators = {"\\+", "-", "/", "\\*"}; //Экранированный массив операторов

        int operatorIndex = -1;
        int operatorCount = 0;
        //Найти опрератор в выражении
        for (int i = 0; i < operators.length; i++) {
                if (input.contains(operators[i])) {
                    operatorIndex = i;
                    operatorCount ++;
                }
            }
        //Если оператор не был найден, выбросить ошибку
        if (operatorIndex == -1) throw new ArithmeticException("В выражении не был найден оператор");
        //Разделить выражение оператором
        String[] nums = input.split(sOperators[operatorIndex]);
        //Если в выражении несколько операторов
        if (operatorCount>1||nums.length>2) throw new ArithmeticException("В выражении допустим только один оператор");

        int num1 = checkNum(nums[0]);
        boolean num1Roman = roman;
        int num2 = checkNum(nums[1]);
        boolean num2Roman = roman;

        //Если операнды в разных системах исчисления
        if (num1Roman != num2Roman)throw new ArithmeticException("Не допустимо использование разных систем исчисления в одном выражении");

        //Если операнды не входят в допустимые рамки от inputMin до inputMax
        if (!(inputMin<= num1 && num1 <=inputMax)||!(inputMin<= num2 && num2 <=inputMax)) {
            throw new ArithmeticException("Калькулятор принимает на вход числа от " + inputMin + " до " + inputMax);
        }

        //Посчитать ответ
        int result = 0;
        String answer = "";

        switch (operators[operatorIndex]){
            case "+":
                result = num1+num2;
                break;
            case "-":
                result = num1-num2;
                break;
            case "/":
                result = num1/num2;
                break;
            case "*":
                result = num1*num2;
                break;
        }

        //Перевести ответ в Римские числа, если необходимо
        if (roman){
            if (result>0){
                answer = intToRoman(result);
            }
            else throw new ArithmeticException("В Римской системе исчисления не допустим ответ меньше 1(I)");
        }
        else {
            answer = Integer.toString(result);
        }

        return (answer);
    }

    //Проверяет является ли строка числом, определяет тип - Римские или Арабские.
    // Возвращает Арабское число или выдает ошибку
    static int checkNum (String sNum){
        //Удаляет пробелы из sNum
        sNum = sNum.replaceAll("\\s", "");
        int num = -1;
        //Пробует найти sNum в rUnits
        for(String n : rUnits){
            if(sNum.equals(n)){
                num = Arrays.asList(rUnits).indexOf(n) + 1;
                break;
            }
        }

        //Если sNum это не Римская цифра
        if(num < 1) {
            roman = false;
            //Пробует перевести введенный Sting в int
            try {
                num = Integer.parseInt(sNum);
                roman = false;
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("В выражении недопустимые операнды. Либо вы пытаетесь использовать Римские числа больше 10(X)");
            }
        }
        else {roman = true;}

        return (num);
    }

    //Конвертирует Римские числа в Арабские
    static String intToRoman(int num){
        String[] hundreds =
                {"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"};
        String[] tens =
                {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
        String[] units =
                {"","I","II","III","IV","V","VI","VII","VIII","IX"};

        return hundreds[num/100]+
                tens[(num%100)/10]+
                units[num%10];
    }

}