package org.emilia;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class GenerateDigitalSignature
  @version 1.0.0
  @since 11.04.2023 - 0:14
*/

import java.io.*;
import java.security.*; //надає методи для підписання даних
public class GenerateDigitalSignature
{
    public static void main(String[] args)
    {
        // Створення підпису DSA
        if (args.length == 1)
        {
            System.out.println("Usage: nameOfFileToSign");
        }
        else try
        {
            /*
            *Створити початкову структуру програми
            *Згенерувати відкритий і закритий ключ
            *Підписати дані
            *Зберегти підпис і відкриті ключі у файлах
            */

            /* Створення пари ключів */

            //RSA (алгоритм цифрового підпису)
            //SUN — стандартний постачальник, вбудований у JDK
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");

            //використовує алгоритм SHA1PRNG , наданий вбудованим постачальником SUN
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

            /* генеруємо пару ключів */

            //для RSA розмір ключа дорівнює 1024
            keyGen.initialize(1024, random);
            KeyPair pair = keyGen.generateKeyPair(); //генеруємо пару ключів


            PrivateKey priv = pair.getPrivate();  //генерує закритий ключ
            PublicKey pub = pair.getPublic();    //генерує відкритий ключ

            /* Створюємо об’єкт підпису та ініціалізуємо його закритим ключем */

            //генерація підпису за допомогою алгоритму DSA
            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
            dsa.initSign(priv); //ініціалізація підпису

            /* Оновлюєм та підписуєм дані */
            FileInputStream fis = new FileInputStream("C:\\cryptLab\\digital.txt");
            BufferedInputStream bufin = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0)
            {
                len = bufin.read(buffer);
                //щоб надати дані
                dsa.update(buffer, 0, len);
            };
            bufin.close();


            /* Тепер, коли всі дані для підпису прочитано, створити для нього підпис */

            byte[] realSig = dsa.sign();
            /* Зберегти підпис у файлі */
            FileOutputStream sigfos = new FileOutputStream("C:\\cryptLab\\signature.txt");
            sigfos.write(realSig);//записує у файл
            sigfos.close();//закриває файл

            /* Зберігаємо відкритий ключ у файлі */
            byte[] key = pub.getEncoded();  //отримання закодованого ключа в байтах
            FileOutputStream keyfos = new FileOutputStream("C:\\cryptLab\\publickey.txt");
            keyfos.write(key);
            keyfos.close();
        }
        catch (Exception e)
        {
            System.err.println("Caught exception " + e.toString());
        }
    };
}