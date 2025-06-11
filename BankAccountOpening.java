import java.util.*;

public class BankAccountOpening {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Account Type (SAVINGS or CURRENT):");
        String accType = sc.nextLine().trim().toUpperCase();

        String customerType = "INDIVIDUAL";
        if (accType.equals("CURRENT")) {
            System.out.println("Enter Customer Type (PROPRIETORSHIP / PARTNERSHIP / COMPANY):");
            customerType = sc.nextLine().trim().toUpperCase();
        }

        List<String> requiredDocs = getRequiredDocs(accType, customerType);

        System.out.println("\nRequired Documents:");
        for (String doc : requiredDocs) {
            System.out.println("- " + doc);
        }

        List<String> submittedDocs = new ArrayList<>();
        System.out.println("\nEnter submitted documents (type 'done' to finish):");
        while (true) {
            String doc = sc.nextLine();
            if ("done".equalsIgnoreCase(doc)) break;
            submittedDocs.add(doc);
        }

        if (new HashSet<>(submittedDocs).containsAll(requiredDocs)) {
            String accountNumber = "AC" + new Random().nextInt(1000000);
            System.out.println("\nAccount opened successfully! Account Number: " + accountNumber);
        } else {
            System.out.println("\nAccount opening failed: Missing required documents.");
        }

        sc.close();
    }

    public static List<String> getRequiredDocs(String accType, String customerType) {
        List<String> docs = new ArrayList<>();

        if ("SAVINGS".equals(accType)) {
            docs = Arrays.asList("ID Proof", "Address Proof", "PAN Card", "Photograph");
        } else if ("CURRENT".equals(accType)) {
            switch (customerType) {
                case "PROPRIETORSHIP":
                    docs = Arrays.asList("PAN Card", "GST Certificate", "Shop License", "ID Proof", "Address Proof");
                    break;
                case "PARTNERSHIP":
                    docs = Arrays.asList("Partnership Deed", "PAN Card", "Address Proof", "Partner KYC");
                    break;
                case "COMPANY":
                    docs = Arrays.asList("Certificate of Incorporation", "PAN Card", "MOA/AOA", "Board Resolution", "Authorized Signatory KYC");
                    break;
                default:
                    System.out.println("Invalid customer type.");
                    break;
            }
        }
        return docs;
    }
}

