package se.akh.gup.zalando;

public class Main {

    public static void main(String[] args){
        String S = "?ab??a";
        char[] strArr = S.toCharArray();
        char[] result = new char[S.length()];

        StringBuilder sb = new StringBuilder();
        int i=1;
        for(Character c: strArr){

            // Case 1: It could be a letter.
                // Check if it matches with corresponding letter to be a palindrome
                // If yes, continue else break and return no.
            // Case 2: It could be a ?
                // Check if corresponding letter is also ?
                    // If yes, then replace both of them with any character and continue
                // In case corresponding letter is not ?
                    // Then update current character with corresponding character value

        }
    }
}
