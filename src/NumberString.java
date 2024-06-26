import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NumberString{
    private final List<Integer> numberString = new LinkedList<>();

    // Konstruktors
    NumberString() {
    }

    // Konstruktors - izveido nejauši ģenerētu skaitļu virkni
    NumberString(int length) {
        int upperLimit = 4; // Augstākais cipars, ko var uzģenerēt virknē
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            int randomDigit = rand.nextInt(upperLimit) + 1;
            numberString.add(randomDigit);
        }
    }

    // Konstruktors - pārveido statisku skaitļu virkni uz sarakstu (Vajadzīgs Node.java implementācijā)
    NumberString (int[] staticArray) {
        for (int j : staticArray) {
            numberString.add(j);
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

    // Pievieno jaunu ciparu beigās
    void addDigit(int newDigit) {
        numberString.add(newDigit);
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
        for (Integer integer : numberString) {
            sB.append(integer);
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

    NumberString createCopy() {
        // Izveido kopiju katram vecā saraksta elementam
        // Speciāli paredzēta, lai izveidotu kopijas katram elementam
        // (lai nebūtu pārkopētas tikai atsauces uz elementiem)

        NumberString newNumberString = new NumberString();
        for (Integer integer : numberString) {
            newNumberString.addDigit(integer);
        }
        return newNumberString;
    }


}
