import java.util.Scanner;

public class FileManagement {
    Folder head = new Folder("Drive C");
    Folder tail = null;
    String Current = head.fileName;
    Scanner hex = new Scanner(System.in);
    Folder temp = null;

    public static void main(String[] args) {
        Scanner ha = new Scanner(System.in);
        FileManagement some = new FileManagement();
        boolean check = true;
        while (check) {
            some.displayName();
            String a = ha.nextLine().trim();
            int ch = 12;
            if (a == "")
                continue;

            if (a.contains("create")) {
                char[] str = a.toCharArray();
                boolean strw = false;
                if (a.contains("-d")) {
                    a = "";
                    for (int i = 0; i < str.length; i++) {
                        if (str[i] == '"') {
                            strw = !strw;
                            continue;
                        }
                        if (strw) {
                            a = a + str[i];
                        }
                    }
                    if (a == "")
                        System.out.println("No folder Name Detected..");
                    else
                        ch = 1;
                }
                if (a.contains("-f")) {
                    a = "";
                    for (int i = 0; i < str.length; i++) {
                        if (str[i] == '"') {
                            strw = !strw;
                            continue;
                        }
                        if (strw) {
                            a = a + str[i];
                        }
                    }
                    if (a == "")
                        System.out.println("No file Name Detected..");
                    else
                        ch = 2;
                }
                if (a.contains("-r"))
                    ch = 9;
            }
            if (a.contains("cd")) {
                if (a.contains("..")) {
                    ch = 3;
                } else {
                    a = a.substring(2, a.length());
                    a = a.trim();
                    ch = 4;
                }
            }
            if (a.contains("/commands")) {
                ch = 8;
            }
            if (a.contains("display")) {
                if (a.contains("-df")) {
                    ch = 5;
                } else if (a.contains("-f")) {
                    ch = 6;
                } else if (a.contains("-d")) {
                    ch = 7;
                } else {
                    System.err.println("Invalid command");
                }
            }

            if (a.contains("exit"))
                ch = 10;
            switch (ch) {
                case 1:
                    some.addFolder(a);
                    System.out.println("Sucessfully Created " + a + " Folder");
                    break;
                case 2:
                    some.createFile(a);
                    System.out.println("Sucessfullt Created " + a + " File");
                    break;
                case 3:
                    some.moveBack();
                    break;
                case 4:
                    some.move(a);
                    break;
                case 7:
                    some.displayFolders();
                    break;
                case 6:
                    some.displayFiles();
                    break;
                case 5:
                    some.displayFiles();
                    some.displayFolders();

                    break;
                case 8:
                    char quest = '"';
                    System.out.println("   commands\n ============\n");
                    System.out.println("   create -d " + quest + "<foldername>" + quest
                            + "  --> Creates a folder with a name\n\n   create -f " + quest + "<fileName>" + quest
                            + "    --> Creates File with a name\n\n   cd <folderName>           --> Go to that particular folder\n\n   cd ..                     --> Go back from current folder\n\n   display -d                --> display folders\n\n   display -f                --> display Files\n\n   display -df              --> display files and folders\n\n   exit  >> for exiting\n\n");
                    break;
                case 9:
                    some.addFolder("desktop");
                    some.addFolder("documents");
                    some.addFolder("downloads");
                    some.addFolder("pictures");
                    some.addFolder("music");
                    System.out.println("Created Requirments");
                    break;
                case 10:
                    check = false;
                    break;
                default:
                    System.err.println("Invalid Command -- > please enter /commands to know more commands");

            }
        }

        System.out.println("exiting...");
    }

    class Folder {
        Folder nextFolder;
        Folder next;
        Folder previous;
        Folder files;
        String fileName;
        Folder nextSave;

        public Folder(String name) {
            this.fileName = name;
            this.previous = null;
            this.next = null;
            this.nextFolder = null;
            this.files = null;
            this.nextSave = null;
        }

        public Folder() {
            this.previous = null;
            this.next = null;
            this.nextFolder = null;
            this.files = null;
            this.nextSave = null;
        }
    }

    public void addFolder(String fileName) {
        if (tail == null) {
            tail = new Folder(fileName);
            head.nextFolder = tail;
            tail.previous = head;
        } else {
            while (tail.next != null) {
                tail = tail.next;
            }
            Folder a = new Folder(fileName);
            tail.next = a;
            a.previous = tail;
            tail = tail.next;
        }
    }

    public void createFile(String name) {
        Folder a = head.files;
        if (a == null) {
            a = new Folder(name);
            head.files = a;
        } else {
            while (a.files != null) {
                a = a.files;
            }
            a.files = new Folder(name);
        }
    }

    public void displayFolders() {
        Folder a = head.nextFolder;
        if (a == null) {
            System.out.println("No Folders Created");
            return;
        }
        System.out.println("Folders:-");
        int i = 1;
        while (a != null) {
            System.out.println(i + " -- " + a.fileName);
            i++;
            a = a.next;
        }
    }

    public void displayFiles() {
        Folder a = head.files;
        if (a == null) {
            System.out.println("No Files Created Yet..");
            return;
        } else {
            int i = 0;
            System.out.println("Files :-");
            while (a != null) {
                System.out.println(++i + " -- " + a.fileName);
                a = a.files;
            }
        }
    }

    public void move(String a) {
        int index = 0;
        Folder check = head.nextFolder;
        if (check == null) {
            System.out.println("Folder is empty");
            return;
        }

        while (!a.equals(check.fileName)) {
            if (check.next != null) {
                check = check.next;
                index++;
            } else {
                System.out.println("fileNotFound");
                return;
            }
        }
        insertNode();
        index++;
        head = head.nextFolder;
        for (int i = 1; i < index; i++) {
            head = head.next;
        }
        tail = head.nextFolder;
        Current = Current + "\\" + head.fileName;
    }

    public void moveBack() {
        int length = 0;
        Folder temps = getTemp();
        if (head.previous == null) {
            System.out.println("all Ready At the Last");
            return;
        }
        if (temps != null) {
            length = head.fileName.length();
        }
        while (head.previous != temps && head.previous != null) {
            head = head.previous;
        }
        if (head.previous != null) {
            head = head.previous;
            tail = head.nextFolder;
        }
        deleteLast();
        Current = Current.substring(0, Current.length() - length - 1);
    }

    public void insertNode() {
        Folder a = temp;
        if (a == null) {
            temp = head;
        } else {
            while (a.nextSave != null) {
                a = a.nextSave;
            }
            a.nextSave = head;
        }
    }

    public void displayBack() {
        Folder a = temp;
        if (a == null)
            return;
        while (a != null) {
            System.out.print(a.fileName + " ");
            a = a.nextSave;
        }
        System.out.print("NUll");

    }

    public void deleteLast() {
        Folder a = temp;
        if (a == null)
            return;
        else {
            if (a.nextSave == null) {
                temp = null;
            } else {
                Folder prev = null;
                while (a.nextSave != null) {
                    prev = a;
                    a = a.nextSave;
                }
                prev.nextSave = null;
            }
        }
    }

    public Folder getTemp() {
        Folder a = temp;
        if (a == null)
            return null;
        else {
            while (a.nextSave != null) {
                a = a.nextSave;
            }
            return a;
        }
    }

    public void displayName() {
        System.out.print(Current + "> ");
    }
}