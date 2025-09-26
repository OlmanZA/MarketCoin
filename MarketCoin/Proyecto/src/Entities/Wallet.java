package Entities;

public class Wallet {
    private String walletNumber;
    private long userCedula;
    private String WalletName;

    public Wallet(String walletNumber, long userCedula,String WalletName) {
        this.walletNumber = walletNumber;
        this.userCedula = userCedula;
        this.WalletName = WalletName;
    }


    public String getWalletName() {
        return WalletName;
    }

    public void setWalletName(String walletName) {
        WalletName = walletName;
    }

    public long getUserCedula() {
        return userCedula;
    }

    public void setUserCedula(long userCedula) {
        this.userCedula = userCedula;
    }

    public void setWalletNumber(String walletNumber) {
        this.walletNumber = walletNumber;
    }


    public String getWalletNumber() {
        return walletNumber;
    }

}