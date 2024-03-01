import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NumberString{
    private List<Integer> numberString;

    // Konstruktors - izveido nejauši ģenerētu skaitļu virkni
    NumberString(int length) {
        int upperLimit = 4; // Augstākais cipars, ko var uzģenerēt virknē
        numberString = new LinkedList<Integer>();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            int randomDigit = rand.nextInt(upperLimit) + 1;
            numberString.add(randomDigit);
        }
    }

    // Izņem konkrētu ciparu no masīva
    int remove(int tookDigit) {
        int index = findIndexOf(tookDigit);
        return numberString.remove(index);
    }

    // Pievieno jaunu ciparu konkrētajā indeksā
    void addDigit(int index, int newDigit) {
        numberString.add(index, newDigit);
    }

    // Aizvieto ciparu konkrētajā indeksā
    void replaceDigit(int index, int newDigit) {
        numberString.set(index, newDigit);
    }

    // Atrod pirmo indeksu konkrētajam ciparam virknē
    int findIndexOf(int digit) {
        for (int i = 0; i < numberString.size(); i++) {
            if (numberString.get(i) == digit) {
                return i;
            }
        }

        return -1;
    }

    // Pārbauda vai skaitļu virkne ir tukša
    boolean isEmpty() {
        return numberString.isEmpty();
    }

    // Šo metodi drošvien izmantos ciparu virknes izvadei uz ekrāna
    // Pārveido ciparu virkni par String
    String convertToString() {
        StringBuilder sB = new StringBuilder();
        for (int i = 0; i < numberString.size(); i++) {
            sB.append(numberString.get(i));
            sB.append(" ");
        }
        return sB.toString();
    }

    // Šo atstāju, ja gadījumā kādam vajag pāriet no dinamiskā masīva uz parasto
    int[] convertToStaticArray() {
        int[] array = new int[numberString.size()];
        for (int i = 0; i < numberString.size(); i++) {
            array[i] = numberString.get(i);
        }
        return array;
    }


}
