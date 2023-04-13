package org.emilia;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class VerifyDigitalSignature
  @version 1.0.0
  @since 11.04.2023 - 0:52
*/

import java.io.*;
import java.security.*;
import java.security.spec.*;

public class VerifyDigitalSignature
{
    public static void main(String[] args)
    {
        /*
         * Підготувати початкову структуру програми
         * Введення та перетворення закодованих байтів відкритого ключа
         *Введення байтів підпису
         *Перевірка підпису
         */


        /* Перевірити підпис DSA */
        if (args.length == 3)
        {
            System.out.println("Usage: publickeyfile signaturefile datafile");
        }
        else try
        {
            /* імпорт закодованого відкритого ключа */
            FileInputStream keyfis = new FileInputStream("C:\\cryptLab\\\\publickey.txt");

            //масив байтів, перетворений у закодовані байти відкритого ключа
            byte[] encKey = new byte[keyfis.available()];
            keyfis.read(encKey);
            keyfis.close();

            //ключова специфікація
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
            KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");

            //згенеровуємо специфікацію відкритого ключа
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

            /* введення байтів підпису */
            FileInputStream sigfis = new FileInputStream("C:\\cryptLab\\\\signature.txt");
            byte[] sigToVerify = new byte[sigfis.available()];  //містить байти підпису
            sigfis.read(sigToVerify );
            sigfis.close();

            /* створюємо об’єкт Signature та ініціалізуємо його відкритим ключем */

            ////об'єкт класу Signature надає назву алгоритму з постачальником
            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
            sig.initVerify(pubKey);

            /* Оновлення та перевірка даних */
            FileInputStream datafis = new FileInputStream("C:\\cryptLab\\\\digital.txt");
            BufferedInputStream bufin = new BufferedInputStream(datafis);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0)
            {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
            };
            bufin.close();

            //повертає true, якщо підпис збігається
            boolean verifies = sig.verify(sigToVerify);
            System.out.println("signature verifies: " + verifies);
        }
        catch (Exception e)
        {
            System.err.println("Caught exception " + e.toString());
        };
    }
}