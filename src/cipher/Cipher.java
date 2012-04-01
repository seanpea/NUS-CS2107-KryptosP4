/**
 * Cipher.java
 *
 * This data structure holds the Plain Text, Cipher Text and the Key
 * that is used during the encryption and decryption process.
 *
 * @author Sean Pea
 * @version 1.0
 */

package cipher;

public abstract class Cipher<E> {
    
    /*
     * Instance Variables
     */
    protected StringBuffer plainText;
    protected StringBuffer cipherText;
    protected E key;
    
    public Cipher(StringBuffer plainText, StringBuffer cipherText, E key) {
        if (plainText == null) {
            this.plainText = new StringBuffer("Cipher Text Not Decrypted");
        } else {
            this.plainText = plainText;
        }
        if (cipherText == null) {
            this.cipherText = new StringBuffer("Plain Text Not Encrypted");
        } else {
            this.cipherText = cipherText;
        }
        this.key = key;
    }
    
    /*
     * Abstract Methods
     */
    public abstract void encrypt();
    public abstract void decrypt();
    
    public StringBuffer getPlainText() {
        return plainText;
    }

    public void setPlainText(StringBuffer plainText) {
        this.plainText = plainText;
    }

    public StringBuffer getCipherText() {
        return cipherText;
    }

    public void setCipherText(StringBuffer cipherText) {
        this.cipherText = cipherText;
    }

    public E getKey() {
        return key;
    }

    public void setKey(E key) {
        this.key = key;
    }
    
    @Override
    public String toString() {
        return "Cipher Text : " + cipherText +
                "\nKey : " + key;
    }
}
