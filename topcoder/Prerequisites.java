// System tested
// BEGIN CUT HERE
// PROBLEM STATEMENT
// 
// ***Note: Please keep programs under 7000 characters in length. Thank you
// 
// 
// Class Name: Prerequisites
// Mathod Name: orderClasses
// Parameters: String[]
// Returns: String[]
// 
// You are a student at a college with the most unbelievably complex
// prerequisite
// structure ever. To help you schedule your classes, you decided to put
// together
// a program that returns the order in which the classes should be taken.
// 
// Implement a class Prerequisites which contains a method orderClasses. The
// method takes a String[] that contains the classes you must take and returns a
// String[] of classes in the order the classes should be taken so that all
// prerequisites are met.
// 
// String[] elements will be of the form (and TopCoder will ensure this):
// "CLASS: PRE1 PRE2 ..." where PRE1 and PRE2 are prerequisites of CLASS. CLASS,
// PRE1, PRE2, ... consist of a department name (3 or 4 capital letters, A-Z
// inclusive) followed by a class number (an integer between 100 and 999,
// inclusive). The department name should be immediately followed by the class
// number with no additional characters, numbers or spaces (i.e. MATH217). It is
// not necessary for a class to have prerequisites. In such a case, the colon is
// the last character in the String.
// 
// You can only take one class at a time, therefore, use the following rules to
// determine which class to take :
// 1) Any prerequisite class(es) listed for a class must be taken before the
// class
// can be taken.
// 2) If multiple classes can be taken at the same time, you take the one with
// the
// lowest number first, regardless of department.
// 3) If multiple classes with the same number can be taken at the same time,
// you
// take the department name which comes first in alphabetical order.
// 4) If the inputted course schedule has errors, return a String[] of length 0.
// There is an error if it is impossible to return the classes in an order such
// that all prerequisites are met, or if a prerequisite is a course that does
// not
// have its own entry in the inputted String[].
// 
// Examples of valid input Strings are:
// "CSE111: CSE110 MATH101"
// "CSE110:"
// 
// Examples of invalid input Strings are:
// "CS117:" (department name must consist of 3 - 4 capital letters, inclusive)
// "cs117:" (department name must consist of 3 - 4 capital letters, inclusive)
// "CS9E11:" (department name must be letters only)
// "CSE110: " (no trailing spaces allowed)
// "CSE110: CSE101 " (no trailing spaces allowed)
// "MATH211: MAM2222" (class number to large)
// "MATH211: MAM22" (class number to small)
// "ENGIN517: MATH211" (department name to large)
// 
// Here is the method signature (be sure your method is public):
// String[] orderClasses(String[] classSchedule);
// 
// TopCoder will make sure classSchedule contains between 1 and 20 Strings,
// inclusive, all of the form above. The Strings will have between 1 and 50
// characters, inclusive. TopCoder will check that the syntax of the Strings are
// correct: The Strings will contain a valid class name, followed by a colon,
// possibly followed by a series of unique prerequisite classes separated by
// single spaces. Also, TopCoder will ensure that each class has at most one
// entry in the String[].
// 
// Examples:
// If classSchedule={
// "CSE121: CSE110",
// "CSE110:",
// "MATH122:",
// }
// The method should return: {"CSE110","CSE121","MATH122"}
// 
// If classSchedule={
// "ENGL111: ENGL110",
// "ENGL110: ENGL111"
// }
// The method should return: {}
// 
// If classSchedule=[
// "ENGL111: ENGL110"
// }
// The method should return: {}
// 
// If classSchedule={
// "CSE258: CSE244 CSE243 INTR100"
// "CSE221: CSE254 INTR100"
// "CSE254: CSE111 MATH210 INTR100"
// "CSE244: CSE243 MATH210 INTR100"
// "MATH210: INTR100"
// "CSE101: INTR100"
// "CSE111: INTR100"
// "ECE201: CSE111 INTR100"
// "ECE111: INTR100"
// "CSE243: CSE254"
// "INTR100:"
// }
// The method should return:
// {"INTR100","CSE101","CSE111","ECE111","ECE201","MATH210","CSE254","CSE221","CSE2
// 43","CSE244","CSE258"}
// 
// 
// DEFINITION
// Class:Prerequisites
// Method:orderClasses
// Parameters:String[]
// Returns:String[]
// Method signature:String[] orderClasses(String[] param0)
// 
// END CUT HERE

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class Prerequisites {

    public String[] orderClasses(String[] classSchedule) {
        String[] retour = new String[classSchedule.length];
        int count = 0;
        while (true) {
            // collect all classes without prereqisites
            Vector matches = new Vector();
            for (int i = 0; i < classSchedule.length; ++i) {
                if (classSchedule[i].matches("[A-Z]+[0-9]+[:]")) {
                    matches.add(classSchedule[i]);
                }
            }
            // if every class has a prerequisite, we have a circle.
            // or we are done
            if (matches.isEmpty()) {
                if (count == classSchedule.length)
                    return retour;
                return new String[0];
            }

            // determine best match
            String best = (String)matches.get(0);
            for (int i=1;i<matches.size();++i) {
                String match = (String)matches.get(i);
                int bestnum = Integer.parseInt(best.replaceAll("[A-Z:]",""));
                int matchnum = Integer.parseInt(match.replaceAll("[A-Z:]",""));
                if (bestnum > matchnum) {
                    best = match;
                }else if (bestnum == matchnum) {
                    String bestString = best.replaceAll("[0-9:]","");
                    String matchString = match.replaceAll("[0-9:]","");
                    if (bestString.compareTo(matchString)>0) {
                        best=match;
                    }
                }
            }
            
            // remove first match from remaining schedule
            String s = best.replaceAll("[:]", "");
            //System.out.println(s);
            for (int j = 0; j < classSchedule.length; ++j) {
                classSchedule[j] = classSchedule[j].replaceAll(s, "").trim();
            }

            // add class to return array
            retour[count++] = s;
        }
    }

    public static void main(String[] args) {
        Prerequisites pr = new Prerequisites();
        String[] sch = new String[] { "CSE258: CSE244 CSE243 INTR100",
                "CSE221: CSE254 INTR100", "CSE254: CSE111 MATH210 INTR100",
                "CSE244: CSE243 MATH210 INTR100", "MATH210: INTR100",
                "CSE101: INTR100", "CSE111: INTR100", "ECE201: CSE111 INTR100",
                "ECE111: INTR100", "CSE243: CSE254", "INTR100:" };
        String[] re = pr.orderClasses(sch);
        for (int i = 0; i < re.length; ++i) { 
            System.out.println(re[i]);
        }

    }
}