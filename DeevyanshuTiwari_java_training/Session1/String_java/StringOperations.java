public class StringOperations {

    static String reverseString(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    static int countVowels(String str) {
        int count = 0;
        for (char c : str.toLowerCase().toCharArray())
            if ("aeiou".indexOf(c) != -1) count++;
        return count;
    }

    static boolean isAnagram(String s1, String s2) {
        char[] a = s1.toLowerCase().toCharArray();
        char[] b = s2.toLowerCase().toCharArray();
        java.util.Arrays.sort(a);
        java.util.Arrays.sort(b);
        return java.util.Arrays.equals(a, b);
    }

    public static void main(String[] args) {
        System.out.println("Reversed: "    + reverseString("Hello"));
        System.out.println("Vowel count: " + countVowels("Hello World"));
        System.out.println("Anagram: "     + isAnagram("listen", "silent"));
    }
}