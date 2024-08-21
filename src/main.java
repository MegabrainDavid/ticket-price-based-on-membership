import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class main {
    public static void main(String[] args) throws Exception {
        Scanner scnr = new Scanner(System.in);
        String tempQuantity, tempSquareInch, Tempsize, drinkType, customerID, firstName, lastName;
        float amountSpent, squareInch;
        char size;
        int quantity, discount = 0, bonus = 0;
        double tempPrice = 0, tempPrice2 = 0;
        boolean check = true, fileExist = true, firstLoop = true;
        Customer[] preCustomerList = null;

        System.out.println("Enter regular customer file");
        String inputFileName = scnr.next();
        FileInputStream inputStream = new FileInputStream(inputFileName);
        Scanner regInFs = new Scanner(inputStream);
        int filesize = 0;

        while (regInFs.hasNextLine()) {
            filesize++;
            String d = regInFs.nextLine();
        }
        regInFs.close();
        FileInputStream InputStream = new FileInputStream(inputFileName);
        Scanner RegInFs = new Scanner(InputStream);
        Customer[] customerList = new Customer[filesize];
        int i = 0;
        while (RegInFs.hasNext()) {
            customerID = RegInFs.next();
            firstName = RegInFs.next();
            lastName = RegInFs.next();
            amountSpent = Float.parseFloat(RegInFs.next());
            customerList[i] = new Customer(customerID, firstName, lastName, amountSpent);

            // System.out.println(customerList[i].getCustomerID());
            i++;
        }
        RegInFs.close();

        System.out.println("Enter prefer customer file");
        inputFileName = scnr.next();
        try {
            FileInputStream preferInputStream = new FileInputStream(inputFileName);
            Scanner preInFs = new Scanner(preferInputStream);
            filesize = 0;

            while (preInFs.hasNextLine()) {
                filesize++;
                String l = preInFs.nextLine();
            }
            // System.out.println(filesize);
            preInFs.close();
            FileInputStream PreferInputStream = new FileInputStream(inputFileName);
            Scanner PreInFs = new Scanner(PreferInputStream);
            preCustomerList = new Customer[filesize];
            i = 0;
            while (PreInFs.hasNext()) {
                customerID = PreInFs.next();
                firstName = PreInFs.next();
                lastName = PreInFs.next();
                amountSpent = Float.parseFloat(PreInFs.next());
                // System.out.println(amountSpent);

                if (amountSpent >= 200) {
                    bonus = Integer.parseInt(PreInFs.next());
                    preCustomerList[i] = new Plat(customerID, firstName, lastName, amountSpent, bonus);
                } else {
                    String tempdiscount = PreInFs.next();
                    String[] info = tempdiscount.split("%");
                    discount = Integer.parseInt(info[0]);

                    preCustomerList[i] = new gold(customerID, firstName, lastName, amountSpent, discount);
                }

                i++;
            }
            PreInFs.close();
        } catch (Exception e) {
            System.out.println("No file found");
            fileExist = false;
        }

        System.out.println("Enter order files");
        inputFileName = scnr.next();
        FileInputStream orderInputStream = new FileInputStream(inputFileName);
        Scanner orderInFs = new Scanner(orderInputStream);
        Customer[] newCustomerList = null;
        Customer[] newPreCustomer = null;
        int line = 0;
        int preLine = 0;
        customerID = "";
        Tempsize = "";
        size = 0;
        drinkType = "";
        tempSquareInch = "";
        squareInch = 0;
        tempQuantity = "";
        quantity = 0;
        while (orderInFs.hasNextLine()) {
            String checkEmpty = orderInFs.nextLine().trim();
            if (checkEmpty.isEmpty()) {
                continue;
            }
            Scanner lineScanner = new Scanner(checkEmpty);
            try {
                customerID = lineScanner.next();
                Tempsize = lineScanner.next();
                size = Tempsize.charAt(0);
                drinkType = lineScanner.next();
                tempSquareInch = lineScanner.next();
                squareInch = Float.parseFloat(tempSquareInch);
                tempQuantity = lineScanner.next();
                quantity = Integer.parseInt(tempQuantity);
                lineScanner.close();
                if (!(drinkType.equals("tea")) && !(drinkType.equals("soda")) && !(drinkType.equals("punch"))) {
                    System.out.println("wrong drink type");
                    continue;
                }
                if (!(size == 'S') && !(size == 'M') && !(size == 'L')) {
                    System.out.println("wrong size type");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("invalid input");
                continue;
            }
            for (int k = 0; k < customerList.length; k++) {
                String tempCustomerID = customerList[k].getCustomerID();
                // System.out.println(tempCustomerID);
                if (tempCustomerID.equals(customerID)) {
                    line = k;
                    // System.out.println(line);
                }
            }
            if (fileExist && firstLoop) {
                for (int j = 0; j < preCustomerList.length; j++) {
                    String tempCustomerID = preCustomerList[j].getCustomerID();
                    if (tempCustomerID.equals(customerID)) {
                        preLine = j;
                    }
                }
            } else if (fileExist && !firstLoop) {
                for (int j = 0; j < newPreCustomer.length; j++) {
                    String tempCustomerID = newPreCustomer[j].getCustomerID();
                    if (tempCustomerID.equals(customerID)) {
                        preLine = j;
                        // System.out.println(preLine);
                    }
                }
            }

            // System.out.println(customerID);
            // System.out.println(drinkType);
            tempPrice = totalPrice(size, drinkType, squareInch, quantity);
            //System.out.println(tempPrice);
            Customer customerToFind = null;
            if (firstLoop) {
                customerToFind = check_id(customerID, customerList);
            } else {
                customerToFind = check_id(customerID, newCustomerList);
            }
            if (customerToFind == null) {
                if (firstLoop) {
                    customerToFind = check_id(customerID, preCustomerList);
                } else {
                    customerToFind = check_id(customerID, newPreCustomer);
                }
                if (customerToFind == null) {
                    System.out.println("Customer not found");
                    continue;
                } else {
                    amountSpent = 0;
                    if (firstLoop) {
                        if (preCustomerList[preLine].getAmountSpent() >= 200) {
                             bonus = ((Plat) preCustomerList[preLine]).getBonus();
                            //System.out.println(newPreCustomer[preLine].getFirstName()
                                 //   + newPreCustomer[preLine].getAmountSpent() + " " + bonus + " " + tempPrice);
                            tempPrice = tempPrice - bonus;
                            amountSpent = ((Plat) preCustomerList[preLine]).getAmountSpent();
                            float amountSpent2 = amountSpent;

                            // System.out.println(amountSpent);
                            amountSpent = amountSpent + (float) tempPrice;
                            amountSpent = (float) (Math.floor(amountSpent * 100) / 100);
                            ((Plat) preCustomerList[preLine]).setAmountSpent(amountSpent);
                            bonus = ((int) amountSpent - (int) amountSpent2) / 5;
                            //System.out.println(amountSpent2);
                            //System.out.println(amountSpent);
                            //System.out.println(bonus);

                            if (bonus < 0) {
                                bonus = 0;
                            }
                            ((Plat) preCustomerList[preLine]).setBonus(bonus);
                        } else {

                            tempPrice2 = tempPrice;
                            //System.out.println(preCustomerList[preLine].getFirstName() + " lol "
                                    //+ preCustomerList[preLine].getAmountSpent());
                            discount = ((gold) preCustomerList[preLine]).getDiscount();
                            tempPrice = tempPrice * ((100 - (double) discount) / 100);
                            amountSpent = ((gold) preCustomerList[preLine]).getAmountSpent();
                            amountSpent = amountSpent + (float) tempPrice;
                            ((gold) preCustomerList[preLine]).setAmountSpent(amountSpent);
                            if (amountSpent > 200) {
                                amountSpent = amountSpent - (float) tempPrice;

                                tempPrice2 *= .85;

                                amountSpent = amountSpent + (float) tempPrice2;
                                bonus = ((int) amountSpent - 200) / 5;
                                if (bonus < 0) {
                                    bonus = 0;
                                }
                            } else if (amountSpent > 150) {
                                amountSpent = amountSpent - (float) tempPrice;
                                tempPrice2 *= .85;
                                discount = 15;
                                amountSpent = amountSpent + (float) tempPrice2;
                            } else if (amountSpent > 100) {
                                amountSpent = amountSpent - (float) tempPrice;
                                tempPrice2 *= .90;
                                discount = 10;
                                amountSpent = amountSpent + (float) tempPrice2;
                            } else {
                                amountSpent = amountSpent - (float) tempPrice;
                                amountSpent = amountSpent + (float) tempPrice;
                            }
                            if (preCustomerList[preLine].getAmountSpent() < 200) {
                                ((gold) preCustomerList[preLine]).setDiscount(discount);
                                ((gold) preCustomerList[preLine]).setAmountSpent(amountSpent);

                            } else {
                                customerID = preCustomerList[preLine].getCustomerID();
                                firstName = preCustomerList[preLine].getFirstName();
                                lastName = preCustomerList[preLine].getLastName();
                                preCustomerList[preLine] = new Plat(customerID, firstName, lastName, amountSpent,
                                        bonus);
                            }
                        }
                    } else if (!firstLoop) {
                        if (newPreCustomer[preLine].getAmountSpent() >= 200) {
                            bonus = ((Plat) newPreCustomer[preLine]).getBonus();
                            //System.out.println(newPreCustomer[preLine].getFirstName()
                                 //   + newPreCustomer[preLine].getAmountSpent() + " " + bonus + " " + tempPrice);
                            tempPrice = tempPrice - bonus;
                            amountSpent = ((Plat) newPreCustomer[preLine]).getAmountSpent();
                            float amountSpent2 = amountSpent;

                            // System.out.println(amountSpent);
                            amountSpent = amountSpent + (float) tempPrice;
                            amountSpent = (float) (Math.floor(amountSpent * 100) / 100);
                            ((Plat) newPreCustomer[preLine]).setAmountSpent(amountSpent);
                            bonus = ((int) amountSpent - (int) amountSpent2) / 5;
                            //System.out.println(amountSpent2);
                            //System.out.println(amountSpent);
                            //System.out.println(bonus);

                            if (bonus < 0) {
                                bonus = 0;
                            }
                            ((Plat) newPreCustomer[preLine]).setBonus(bonus);
                        } else {
                            tempPrice2 = tempPrice;
                            //System.out.println(newPreCustomer[preLine].getFirstName() + " "
                                   // + newPreCustomer[preLine].getAmountSpent() + " " + tempPrice);
                            discount = ((gold) newPreCustomer[preLine]).getDiscount();
                            tempPrice = tempPrice * ((100 - (double) discount) / 100);
                            amountSpent = ((gold) newPreCustomer[preLine]).getAmountSpent();
                            amountSpent = amountSpent + (float) tempPrice;
                           // System.out.println(
                                    //newPreCustomer[preLine].getFirstName() + " " + amountSpent + " " + tempPrice);
                            ((gold) newPreCustomer[preLine]).setAmountSpent(amountSpent);
                            if (amountSpent > 200) {
                                amountSpent = amountSpent - (float) tempPrice;

                                tempPrice2 *= .85;

                                amountSpent = amountSpent + (float) tempPrice2;
                                bonus = ((int) amountSpent - 200) / 5;
                                if (bonus < 0) {
                                    bonus = 0;
                                }
                            } else if (amountSpent > 150) {
                                amountSpent = amountSpent - (float) tempPrice;
                                tempPrice2 *= .85;
                                discount = 15;
                                amountSpent = amountSpent + (float) tempPrice2;
                            } else if (amountSpent > 100) {
                                amountSpent = amountSpent - (float) tempPrice;
                                System.out.println(amountSpent + " lol");
                                tempPrice2 *= .90;
                                discount = 10;
                                amountSpent = amountSpent + (float) tempPrice2;
                            } else {
                                amountSpent = amountSpent - (float) tempPrice;
                                amountSpent = amountSpent + (float) tempPrice;
                            }
                            if (newPreCustomer[preLine].getAmountSpent() < 200) {
                                ((gold) newPreCustomer[preLine]).setDiscount(discount);
                                ((gold) newPreCustomer[preLine]).setAmountSpent(amountSpent);
                            } else {
                                customerID = newPreCustomer[preLine].getCustomerID();
                                firstName = newPreCustomer[preLine].getFirstName();
                                lastName = newPreCustomer[preLine].getLastName();
                               // System.out.println(
                                  //      newPreCustomer[preLine].getFirstName() + " " + amountSpent + " " + tempPrice);
                                //System.out.println(bonus);
                                newPreCustomer[preLine] = new Plat(customerID, firstName, lastName, amountSpent, bonus);
                            }
                        }
                    }
                }
            } else {
                // System.out.println(line);
                amountSpent = customerList[line].getAmountSpent();
                amountSpent = amountSpent + (float) tempPrice;
                customerList[line].setAmountSpent(amountSpent);
                // System.out.println(customerList[2].getCustomerID());
                // System.out.println(customerList[2].getAmountSpent());
                // System.out.println(amountSpent);
                if (amountSpent > 50) {
                    check = false;
                    customerID = customerList[line].getCustomerID();
                    firstName = customerList[line].getFirstName();
                    lastName = customerList[line].getLastName();
                    // amountSpent = customerList[line].getAmountSpent();

                    discount = 0;
                    // System.out.println(tempPrice);
                    if (amountSpent > 150) {
                        amountSpent = amountSpent - (float) tempPrice;
                        tempPrice *= .85;
                        discount = 15;
                    } else if (amountSpent > 100) {
                        amountSpent = amountSpent - (float) tempPrice;
                        tempPrice *= .90;
                        discount = 10;
                    } else if (amountSpent > 50) {
                        amountSpent = amountSpent - (float) tempPrice;
                        tempPrice *= 0.95;
                        discount = 5;
                    }
                    // System.out.println(tempPrice);
                    amountSpent = amountSpent + (float) tempPrice;
                    customerList[line].setAmountSpent(amountSpent);
                    // System.out.println(customerList[line].getAmountSpent());
                    Customer[] promotion = new Customer[1];
                    if (firstLoop) {
                        newCustomerList = remove_customer(customerToFind, customerList);
                        promotion[0] = new gold(customerID, firstName, lastName, amountSpent, discount);
                        newPreCustomer = put_customer(customerToFind, preCustomerList, promotion, fileExist);
                        firstLoop = false;
                    } else {
                        newCustomerList = remove_customer(customerToFind, newCustomerList);
                        promotion[0] = new gold(customerID, firstName, lastName, amountSpent, discount);
                        newPreCustomer = put_customer(customerToFind, newPreCustomer, promotion, fileExist);
                    }
                }
            }

        }

        orderInFs.close();
        scnr.close();
        if (check == true) {
            newCustomerList = Arrays.copyOf(customerList, customerList.length);
            newPreCustomer = Arrays.copyOf(preCustomerList, preCustomerList.length);
        }
        FileOutputStream outputStream = new FileOutputStream("customer.dat");
        PrintWriter write = new PrintWriter(outputStream);
        for (int p = 0; p < newCustomerList.length; p++) {
            amountSpent = newCustomerList[p].getAmountSpent();
            write.print(newCustomerList[p].getCustomerID() + " " + newCustomerList[p].getFirstName() + " "
                    + newCustomerList[p].getLastName() + " ");
            write.printf("%.2f", amountSpent);
            write.println();
        }
        write.close();
        FileOutputStream preOutputStream = new FileOutputStream("preferred.dat");
        PrintWriter preWrite = new PrintWriter(preOutputStream);
        for (int m = 0; m < newPreCustomer.length; m++) {
            preWrite.print(newPreCustomer[m].getCustomerID() + " " + newPreCustomer[m].getFirstName() + " "
                    + newPreCustomer[m].getLastName() + " ");
            preWrite.printf("%.2f", newPreCustomer[m].getAmountSpent());
            preWrite.print(" " + newPreCustomer[m].toString());

            preWrite.println();
        }
        preWrite.close();
    }

    public static double totalPrice(char size, String drinkType, float squareInch, int quantity) throws Exception {
        double diameter = 0;
        double height = 0;
        double ounce = 0;
        double surfaceArea = 0;
        double graphicPrice = 0;
        double totalPrice = 0;
        double ouncePrice = 0;

        switch (drinkType) {
            case "soda":
                ouncePrice = 0.20;
                break;
            case "tea":
                ouncePrice = 0.12;
                break;
            case "punch":
                ouncePrice = 0.15;
                break;
            default:
                System.out.println("invalid type");
                break;
        }
        if (size == 'S') {
            diameter = 4;
            height = 4.5;
            ounce = 12;
        } else if (size == 'M') {
            diameter = 4.5;
            height = 5.75;
            ounce = 20;
        } else if (size == 'L') {
            diameter = 5.5;
            height = 7.00;
            ounce = 32;
        }
        surfaceArea = diameter * height * Math.PI;
        surfaceArea = (double) Math.round(surfaceArea * 100) / 100;
        ouncePrice = ounce * ouncePrice;
        graphicPrice = surfaceArea * squareInch;
        totalPrice = graphicPrice + ouncePrice;
        totalPrice = totalPrice * quantity;
        totalPrice = (double) Math.round(totalPrice * 100) / 100;
        // System.out.println(totalPrice);
        return totalPrice;
    }

    public static Customer check_id(String id, Customer[] array) {
        for (Customer customer : array) {
            if (customer != null && customer.getCustomerID().equals(id)) {
                return customer;
            }
        }
        return null; // Customer not found in the list
    }

    public static Customer[] remove_customer(Customer cus, Customer[] array) {
        Customer[] new_array = new Customer[array.length - 1];
        int k = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && !cus.equals(array[i])) {
                new_array[k] = array[i];
                k++;
            }
        }
        return new_array;
    }

    public static Customer[] put_customer(Customer cus, Customer[] array, Customer[] promo, boolean fileExist) {
        if (array == null && !fileExist) {
            // System.out.println(1);
            Customer[] new_array = new Customer[1];
            new_array[0] = promo[0];
            return new_array;
        } else if (array == null) {
            // System.out.println(2);
            Customer[] new_array = new Customer[1];
            new_array[0] = cus;
            return new_array;
        }
        Customer[] new_array = Arrays.copyOf(array, array.length + 1);
        new_array[array.length] = promo[0];
        return new_array;
    }

}
