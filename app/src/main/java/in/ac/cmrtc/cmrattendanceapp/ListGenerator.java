package in.ac.cmrtc.cmrattendanceapp;

import java.util.ArrayList;

public class ListGenerator {
    //variable to store generated list
    private static final ArrayList<String> rollNo = new ArrayList<>();
    //variable to store branch, year & section information
    private static int branch, year, section;
    //Constructor to the class takes branch, year & section
    ListGenerator(int branch, int year, int section) {
        rollNo.clear(); //removing any previously generated list
        //Loading passed variables to class variables
        ListGenerator.branch = branch;
        ListGenerator.year = year;
        ListGenerator.section = section;
    }
    //Method to return the generated list
    public ArrayList<String> getList() {
        generate(); //Method to generate the list
        return rollNo;
    }

    private void generate() {
        switch (branch) {//1

            case 5:
                switch (year) {  //2

                    case 2:
                        switch (section) {  //3
                            case 1:
                                Cse_2_A();
                                break;
                            case 2:
                                Cse_2_B();
                                break;
                            case 3:
                                Cse_2_C();
                                break;
                            case 4:
                                Cse_2_D();
                                break;

                        }
                        break;
                    case 3:
                        switch (section) {  //3
                            case 1:
                                Cse_3_A();
                                break;
                            case 2:
                                Cse_3_B();
                                break;
                            case 3:
                                Cse_3_C();
                                break;
                            case 4:
                                Cse_3_D();
                                break;

                        }
                        break;
                    case 4:
                        switch (section) {  //3
                            case 1:
                                Cse_4_A();
                                break;
                            case 2:
                                Cse_4_B();
                                break;
                            case 3:
                                Cse_4_C();
                                break;
                            case 4:
                                Cse_4_D();
                                break;

                        }
                        break;
                }
                break;
        }
    }


    //CSE Students list

    private void Cse_2_A() {
        //Storing Roll No. in array list using loops
        for (char i = '0'; i <= '5'; i++) { //This loop generates the last second character of roll no.
            for (int j = 0; j < 10; j++) { //Tis loop generates an last character of roll no.

                //The generated character and integer art converted to string and concatenated
                //to store in array list
                rollNo.add("5" + Character.toString(i) + Integer.toString(j));
            }
        }
        //Removing the no.'s which are not required
        rollNo.remove("500");
        rollNo.remove("532");
        rollNo.remove("533");
        rollNo.remove("552");
        //Adding no.'s which were unable to add using above loop
        rollNo.add("16-501");
        rollNo.add("16-503");
        rollNo.add("16-504");
        rollNo.add("16-505");
        rollNo.add("16-506");
    }

    private void Cse_2_B() {
        //Storing Roll No. in array list using loops
        for (char i = '6'; i <= 'B'; i++) { //This loop generates the last second character of roll no.
            if (i == ':')
                while (i != 'A')
                    i++;
            for (int j = 0; j < 10; j++) { //Tis loop generates an last character of roll no.

                //The generated character and integer art converted to string and concatenated
                //to store in array list
                rollNo.add("5" + Character.toString(i) + Integer.toString(j));
            }
        }
        //Removing the no.'s which are not required
        rollNo.remove("560");
        rollNo.remove("565");
        rollNo.remove("566");
        rollNo.remove("575");
        rollNo.remove("579");
        rollNo.remove("585");
        rollNo.remove("5A7");
        rollNo.remove("5B0");
        rollNo.remove("5B8");
        //Adding no.'s which were unable to add using above loop
        rollNo.add("5C0");
        rollNo.add("16-507");
        rollNo.add("16-508");
        rollNo.add("16-509");
        rollNo.add("16-510");
        rollNo.add("16-511");
        rollNo.add("16-512");
    }

    private void Cse_2_C() {
        //Storing Roll No. in array list using loops
        for (char i = 'C'; i <= 'H'; i++) { //This loop generates the last second character of roll no.
            for (int j = 0; j < 10; j++) { //Tis loop generates an last character of roll no.

                //The generated character and integer art converted to string and concatenated
                //to store in array list
                rollNo.add("5" + Character.toString(i) + Integer.toString(j));
            }
        }

        //Removing the no.'s which are not required
        rollNo.remove("5C0");
        rollNo.remove("5C1");
        rollNo.remove("5D3");
        rollNo.remove("5D9");
        rollNo.remove("5E8");
        rollNo.remove("5F1");
        rollNo.remove("5G9");
        rollNo.remove("5H4");

        //Adding no.'s which were unable to add using above loop
        rollNo.add("5J0");
        rollNo.add("16-513");
        rollNo.add("16-514");
        rollNo.add("16-515");
        rollNo.add("16-516");
        rollNo.add("16-517");
        rollNo.add("16-518");

    }

    private void Cse_2_D() {
        //Storing Roll No. in array list using loops
        for (char i = 'J'; i <= 'P'; i++) { //This loop generates the last second character of roll no.
            if (i == 'O')
                i++;
            for (int j = 0; j < 10; j++) { //Tis loop generates an last character of roll no.

                //The generated character and integer art converted to string and concatenated
                //to store in array list
                rollNo.add("5" + Character.toString(i) + Integer.toString(j));
            }
        }
        //Removing the no.'s which are not required
        rollNo.remove("5J0");
        rollNo.remove("5J4");
        rollNo.remove("5K3");
        rollNo.remove("5K7");
        rollNo.remove("5K8");
        rollNo.remove("5M9");
        rollNo.remove("5P7");

        //Adding no.'s which were unable to add using above loop
        rollNo.add("5Q0");
        rollNo.add("16-519");
        rollNo.add("16-521");
        rollNo.add("16-522");
        rollNo.add("16-523");
    }

    private void Cse_3_A() {
        //Storing Roll No. in array list using loops
        for (char i = '0'; i <= '5'; i++) { //This loop generates the last second character of roll no.
            for (int j = 0; j < 10; j++) { //Tis loop generates an last character of roll no.

                //The generated character and integer art converted to string and concatenated
                //to store in array list
                rollNo.add("5" + Character.toString(i) + Integer.toString(j));
            }
        }
        //Removing the no.'s which are not required
        rollNo.remove("500");
        rollNo.remove("530");
        rollNo.remove("534");
        rollNo.remove("543");
        rollNo.remove("554");

        //Adding no.'s which were unable to add using above loop
        rollNo.add("560");
        rollNo.add("13-556");
        rollNo.add("13-523");
        rollNo.add("13-588");
        rollNo.add("14-562");
        rollNo.add("14-5N4");
    }

    private void Cse_3_B() {
        //Storing Roll No. in array list using loops
        for (char i = '6'; i <= 'B'; i++) { //This loop generates the last second character of roll no.
            if (i == ':')
                while (i != 'A')
                    i++;
            for (int j = 0; j < 10; j++) { //Tis loop generates an last character of roll no.

                //The generated character and integer art converted to string and concatenated
                //to store in array list
                rollNo.add("5" + Character.toString(i) + Integer.toString(j));
            }
        }
        //Removing the no.'s which are not required
        rollNo.remove("560");
        rollNo.remove("563");
        rollNo.remove("569");
        rollNo.remove("587");
        rollNo.remove("596");
        rollNo.remove("5A9");
        //Adding no.'s which were unable to add using above loop
        rollNo.add("5C0");
        rollNo.add("13-508");
        rollNo.add("13-582");
        rollNo.add("13-5E1");
        rollNo.add("14-508");
        rollNo.add("15-501");
        rollNo.add("15-502");
        rollNo.add("15-503");
        rollNo.add("15-504");
    }

    private void Cse_3_C() {
        //Storing Roll No. in array list using loops
        for (char i = 'C'; i <= 'H'; i++) { //This loop generates the last second character of roll no.
            for (int j = 0; j < 10; j++) { //Tis loop generates an last character of roll no.

                //The generated character and integer art converted to string and concatenated
                //to store in array list
                rollNo.add("5" + Character.toString(i) + Integer.toString(j));
            }
        }
        //Removing the no.'s which are not required
        rollNo.remove("5C0");
        rollNo.remove("5D1");
        rollNo.remove("5F0");
        rollNo.remove("5G3");
        rollNo.remove("5G4");
        rollNo.remove("5F1");
        rollNo.remove("5G9");
        rollNo.remove("5H4");

        //Adding no.'s which were unable to add using above loop
        rollNo.add("5J0");
        rollNo.add("15-506");
        rollNo.add("15-507");
        rollNo.add("15-510");
    }

    private void Cse_3_D() {
        //Storing Roll No. in array list using loops
        for (char i = 'J'; i <= 'P'; i++) { //This loop generates the last second character of roll no.
            if (i == 'O')
                i++;
            for (int j = 0; j < 10; j++) { //Tis loop generates an last character of roll no.

                //The generated character and integer art converted to string and concatenated
                //to store in array list
                rollNo.add("5" + Character.toString(i) + Integer.toString(j));
            }
        }
        //Removing the no.'s which are not required
        rollNo.remove("5J0");
        rollNo.remove("5L1");
        rollNo.remove("5L8");
        rollNo.remove("5M8");
        rollNo.remove("5N1");
        rollNo.remove("5N4");
        rollNo.remove("5P9");

        //Adding no.'s which were unable to add using above loop
        rollNo.add("5Q0");
        rollNo.add("13-5C6");
        rollNo.add("13-5E7");
        rollNo.add("15-508");
        rollNo.add("15-509");
    }

    private void Cse_4_A() {
        //Storing Roll No. in array list using loops
        for (char i = '0'; i <= '5'; i++) { //This loop generates the last second character of roll no.
            for (int j = 0; j < 10; j++) { //Tis loop generates an last character of roll no.

                //The generated character and integer art converted to string and concatenated
                //to store in array list
                rollNo.add("5" + Character.toString(i) + Integer.toString(j));
            }
        }
        //Removing the no.'s which are not required
        rollNo.remove("500");
        rollNo.remove("505");
        rollNo.remove("508");
        rollNo.remove("513");
        rollNo.remove("523");
        rollNo.remove("554");
        rollNo.remove("556");

        //Adding no.'s which were unable to add using above loop
        rollNo.add("560");
        rollNo.add("12-573");
        rollNo.add("12-594");
        rollNo.add("12-5A6");
    }

    private void Cse_4_B() {
        //Storing Roll No. in array list using loops
        for (char i = '6'; i <= 'B'; i++) { //This loop generates the last second character of roll no.
            if (i == ':')
                while (i != 'A')
                    i++;
            for (int j = 0; j < 10; j++) { //Tis loop generates an last character of roll no.

                //The generated character and integer art converted to string and concatenated
                //to store in array list
                rollNo.add("5" + Character.toString(i) + Integer.toString(j));
            }
        }
        //Removing the no.'s which are not required
        rollNo.remove("560");
        rollNo.remove("574");
        rollNo.remove("582");
        rollNo.remove("588");
        rollNo.remove("593");
        rollNo.remove("5A6");
        rollNo.remove("5B8");
        //Adding no.'s which were unable to add using above loop
        rollNo.add("5C0");
        rollNo.add("14-501");
        rollNo.add("14-502");
    }

    private void Cse_4_C() {
        //Storing Roll No. in array list using loops
        for (char i = 'C'; i <= 'H'; i++) { //This loop generates the last second character of roll no.
            for (int j = 0; j < 10; j++) { //Tis loop generates an last character of roll no.

                //The generated character and integer art converted to string and concatenated
                //to store in array list
                rollNo.add("5" + Character.toString(i) + Integer.toString(j));
            }
        }
        //Removing the no.'s which are not required
        rollNo.remove("5C0");
        rollNo.remove("5C2");
        rollNo.remove("5C6");
        rollNo.remove("5C9");
        rollNo.remove("5D1");
        rollNo.remove("5D4");
        rollNo.remove("5E1");
        rollNo.remove("5E7");
        rollNo.remove("5F7");
        rollNo.remove("5F8");
        //Adding no.'s which were unable to add using above loop
        rollNo.add("5J0");
    }

    private void Cse_4_D() {
        //Storing Roll No. in array list using loops
        for (char i = 'J'; i <= 'P'; i++) { //This loop generates the last second character of roll no.
            if (i == 'O')
                i++;
            for (int j = 0; j < 10; j++) { //Tis loop generates an last character of roll no.

                //The generated character and integer art converted to string and concatenated
                //to store in array list
                rollNo.add("5" + Character.toString(i) + Integer.toString(j));
            }
        }
    }
}
