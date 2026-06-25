package ma.chu.gestiondeces.dashboard;

public class DashboardStats {

    private long totalDossiers;
    private long enAttenteVerification;
    private long pretPourTransfert;
    private long enCoursTransfert;
    private long clotures;

    public DashboardStats() {
    }

    public DashboardStats(long totalDossiers, long enAttenteVerification, long pretPourTransfert, long enCoursTransfert, long clotures) {
        this.totalDossiers = totalDossiers;
        this.enAttenteVerification = enAttenteVerification;
        this.pretPourTransfert = pretPourTransfert;
        this.enCoursTransfert = enCoursTransfert;
        this.clotures = clotures;
    }

    public long getTotalDossiers() {
        return totalDossiers;
    }

    public long getEnAttenteVerification() {
        return enAttenteVerification;
    }

    public long getPretPourTransfert() {
        return pretPourTransfert;
    }

    public long getEnCoursTransfert() {
        return enCoursTransfert;
    }

    public long getClotures() {
        return clotures;
    }

    public void setTotalDossiers(long totalDossiers) {
        this.totalDossiers = totalDossiers;
    }

    public void setEnAttenteVerification(long enAttenteVerification) {
        this.enAttenteVerification = enAttenteVerification;
    }

    public void setPretPourTransfert(long pretPourTransfert) {
        this.pretPourTransfert = pretPourTransfert;
    }

    public void setEnCoursTransfert(long enCoursTransfert) {
        this.enCoursTransfert = enCoursTransfert;
    }

    public void setClotures(long clotures) {
        this.clotures = clotures;
    }
}