package org.emilia;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class DiffieHellman
  @version 1.0.0
  @since 15.03.2023 - 19:56
*/

import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellman {

    public static void main(String[] args) {
        // Вибираємо просте число р
        BigInteger p = generatePrimeNumber(40);

        System.out.println("A simple number p = " + p);

        // Знаходимо первісний корінь а
        BigInteger a = findPrimitiveRoot(p);
        System.out.println("The original root a = " + a);

        // Генеруємо випадкові секретні числа А і В для 1людини та 2людини
        BigInteger aSecret = generateSecretNumber(p);
        BigInteger bSecret = generateSecretNumber(p);

        // Обчислюємо відкриті ключі для А обчислює та В обчислює
        BigInteger aOpenKey = a.modPow(aSecret, p);
        System.out.println("Open key for A: " + aOpenKey);
        BigInteger bOpenKey = a.modPow(bSecret, p);
        System.out.println("Open key for B: " + bOpenKey);

        //обмінюємо відкриті ключі та обчислюємо спільний ключ
        BigInteger aSharedKey = bOpenKey.modPow(aSecret, p);
        System.out.println("Shared key for A: " + aSharedKey);
        BigInteger bSharedKey = aOpenKey.modPow(bSecret, p);
        System.out.println("Shared key for B: " + bSharedKey);

        //порівнюю спільні ключі
        if (aSharedKey.equals(bSharedKey)) {
            System.out.println("Shared key successfully generated: " + aSharedKey);
        } else {
            System.out.println("Error: Shared key does not match.");
        }
    }

    // Метод для генерації випадкового простого числа заданої довжини бітів
    public static BigInteger generatePrimeNumber(int bitLength) {
        SecureRandom random = new SecureRandom();
        return BigInteger.probablePrime(bitLength, random);
    }

    // Метод для знаходження первісного кореня за модулем р
    public static BigInteger findPrimitiveRoot(BigInteger p) {
    //обчислюється значення функції Ейлера phi(p) = p-1,яка є кількістю натуральних чисел менших та взаємно простих за p
        BigInteger phi = p.subtract(BigInteger.ONE);

    //для кожного числа i від 2 до phi перевіряється чи є i дільником phi та чи a = i^(phi/i)mod(p)=1
    //якщо таке число a знайдене, то i є первісним коренем за модулем p
        /*for (BigInteger i = BigInteger.TWO; i.compareTo(phi) < 0; i = i.add(BigInteger.ONE)) {
            if (phi.mod(i).equals(BigInteger.ZERO) && i.modPow(phi.divide(i), p).equals(BigInteger.ONE)) {
                return i;
            }
        }*/
        return null;
    }

    // Метод для генерації випадкового секретного числа
    public static BigInteger generateSecretNumber(BigInteger p) {
        SecureRandom random = new SecureRandom();
        BigInteger secret = new BigInteger(p.bitLength(), random);
        while (secret.compareTo(BigInteger.ZERO) <= 0 || secret.compareTo(p.subtract(BigInteger.ONE)) >= 0) {
            secret = new BigInteger(p.bitLength(), random);
        }
        return secret;
    }

}

/* //619906883287
1. Проблема розподілу ключів у симетричних криптосистемах полягає в тому, що дві сторони, які бажають зв'язатися
 зашифрованими повідомленнями, повинні попередньо домовитися про спосіб і ключ для шифрування та розшифрування
  повідомлень. Це може бути складно та небезпечно, оскільки ключі можуть бути скомпрометовані або перехоплені.

2. Асиметричні криптосистеми вирішують цю проблему за допомогою використання пар ключів: публічного та приватного.
 Публічний ключ відомий всім, хто бажає зашифрувати повідомлення для власника приватного ключа, який може розшифрувати
  його. Приватний ключ відомий тільки власнику, який може використовувати його для підпису повідомлень. Таким чином,
   асиметрична криптосистема забезпечує безпечний обмін повідомленнями, не вимагаючи передачі ключів між сторонами.

3. Комбіновані криптосистеми поєднують в собі переваги симетричних та асиметричних криптосистем. Вони можуть
 використовувати швидкі та ефективні симетричні алгоритми для шифрування повідомлень, а також асиметричні алгоритми для
  безпечного розподілу ключів. Недоліком є складність реалізації та збільшення обчислювальних витрат.

4. Метод Діффі-Хеллмана - це метод відкритого розподілу ключів, який дозволяє двом сторонам встановити спільний
 секретний ключ через незахищений канал зв'язку. Кожна сторона генерує приватний ключ та публічний ключ, який
  відправляється іншій стороні. За допомогою цих ключів та обчислень за модулем генерується спільний секретний ключ,
   який використовується для шифрування та розшифрування повідомлень.

5.Крипостійкість методу Діффі-Хеллмана базується на складності обчислень дискретного логарифму в обмеженому просторі
 чисел, що робить його надзвичайно складним для зламування.

6.  Одностороння функція - це математична функція, яка легко обчислюється в один напрямок, але надзвичайно складно
 обчислюється в зворотному напрямку. Іншими словами, її легко застосовувати для шифрування даних, але важко розшифрувати
  зашифровані дані без належного ключа.

7. 1. Функція хешування SHA-256 - це одностороння функція, яка приймає вхідні дані будь-якого розміру і повертає вихідні
 дані фіксованої довжини. Ця функція використовується для створення дайджестів повідомлень та забезпечення цілісності
  даних.

2. RSA - це криптографічний алгоритм, який базується на складності факторизації великих чисел. Використовується для
 шифрування та підпису повідомлень.

3. Дискретний логарифм - це математична проблема, яка полягає в знаходженні показника степеня відомого числа за модулем.
 Ця проблема використовується в асиметричних криптосистемах, таких як Diffie-Hellman та ElGamal.

4. Елліптична криптографія - це криптографічний метод, який використовує властивості еліптичних кривих для шифрування
 та підпису повідомлень. В основі цього методу лежить вирішення проблеми дискретного логарифму на еліптичних кривих.

8.
 */

