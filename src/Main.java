import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:mysql://192.168.56.101:4567/madang";
    private static final String USER = "dongmin-lee";
    private static final String PASS = "1234";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("항목들 추가했습니다. 삽입 삭제 검색:");
            System.out.println("1. 책추가");
            System.out.println("2. 책삭제");
            System.out.println("3. 책검색");
            System.out.println("4. 잘 되었나 확인해보기 ");
            System.out.println("5. 종료");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("책번호 책이름 출판사 가격을 써주세요:");
                    int bookid = Integer.parseInt(scanner.nextLine());
                    String bookname = scanner.nextLine();
                    String publisher = scanner.nextLine();
                    int price = Integer.parseInt(scanner.nextLine());
                    insertBook(bookid, bookname, publisher, price);
                    break;
                case 2:
                    System.out.println("삭제할 책 이름을 선택하여 주세요");
                    String delectname = scanner.nextLine();
                    deleteBook(delectname);
                    break;
                case 3:
                    System.out.println("찾아볼 책을 말해주세요");
                    String searchbook = scanner.nextLine();
                    searchBooks(searchbook);
                    break;
                case 4:
                    System.out.println("디비를 확인해 보자");
                    searchallBooks();
                    break;
                case 5:
                    return;
            }
        }
    }

    private static void insertBook(int bookid, String bookname, String publisher, int price) {
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO Book(bookid , bookname, publisher, price) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bookid);
            pstmt.setString(2, bookname);
            pstmt.setString(3, publisher);
            pstmt.setInt(4, price);
            pstmt.executeUpdate();
            System.out.println("책 추가됨");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteBook(String delectname) {
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "DELETE FROM Book WHERE bookname = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, delectname);
            pstmt.executeUpdate();
            System.out.println("책 삭제됨");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchBooks(String searchbook) {
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM Book WHERE bookname LIKE ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, "%" + searchbook + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchallBooks(){
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM Book";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}