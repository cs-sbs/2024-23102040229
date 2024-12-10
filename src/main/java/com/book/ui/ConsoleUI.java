package com.book.ui;

import com.book.excel.ExcelExporter;
import com.book.pojo.Book;
import com.book.pojo.User;
import com.book.service.BookService;
import com.book.service.UserService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleUI {
    private final BookService bookService = new BookService();
    private final UserService userService = new UserService();
    private final Scanner scanner = new Scanner(System.in);
    private User currentUser;



    public void start() {
        while (true) {
            if (currentUser == null) {
                showMainMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private void showMainMenu() {
        System.out.println("欢迎来到图书管理系统");
        System.out.println("1. 注册");
        System.out.println("2. 登录");
        System.out.println("3. 退出");
        System.out.println("4. 数据存放到文件");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 清除换行符
        switch (choice) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            case 3:
                exit();
                return;
            case 4:
                exportDataToFile();
                break;
            default:
                System.out.println("无效选项，请重新选择。");
        }
    }

    private void exportDataToFile() {
        // 假设你已经有了一个方法可以获取所有的书籍记录
        List<Book> books = bookService.getAllBooks();

        // 指定输出文件路径
        String excelFilePath = "R:/cun.xlsx";

        try {
            ExcelExporter.exportToExcel(books, excelFilePath);
            System.out.println("数据写入文件成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showUserMenu() {
        System.out.println("请选择操作：");
        System.out.println("1. 查看所有书籍");
        System.out.println("2. 搜索书籍");
        if (currentUser.getRole() == User.Role.ADMIN) {
            System.out.println("3. 添加书籍");
            System.out.println("4. 删除书籍");
        }

        // 提交

        // 分类查询



        System.out.println("5. 注销登录");
        System.out.println("6. 退出系统");
        System.out.println("7. 分类查询");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 清除换行符
        switch (choice) {
            case 1:
                viewBooks();
                break;
            case 2:
                searchBooks();
                break;
            case 3:
                if (currentUser.getRole() == User.Role.ADMIN) {
                    addBook();
                } else {
                    System.out.println("权限不足，无法添加书籍。");
                }
                break;
            case 4:
                if (currentUser.getRole() == User.Role.ADMIN) {
                    deleteBook();
                } else {
                    System.out.println("权限不足，无法删除书籍。");
                }
                break;
            case 5:
                logout();
                break;
            case 6:
                exit();
                return;
                case 7:
                showCategoryMenu();
                break;
            default:
                System.out.println("无效选项，请重新选择。");
        }
    }

    private void register() {
        System.out.print("请输入用户名: ");
        String username = scanner.nextLine();
        System.out.print("请输入密码: ");
        String password = scanner.nextLine();

        // 检查是否为管理员注册
        boolean isAdmin = false;
        if ("admin".equalsIgnoreCase(username)) {
            System.out.print("请输入管理员密钥: ");
            String adminKey = scanner.nextLine();
            if ("supersecretkey".equals(adminKey)) { // 使用更安全的方式处理密钥
                isAdmin = true;
            } else {
                System.out.println("无效的管理员密钥。");
                return;
            }
        }

        User.Role role = isAdmin ? User.Role.ADMIN : User.Role.USER;
        boolean success = userService.register(username, password, role);
        if (success) {
            System.out.println("注册成功！");
        } else {
            System.out.println("注册失败。用户名可能已存在。");
        }
    }

    private void login() {
        System.out.print("用户名: ");
        String username = scanner.nextLine();
        System.out.print("密码: ");
        String password = scanner.nextLine();

        currentUser = userService.login(username, password);
        if (currentUser != null) {
            System.out.println("登录成功！");
        } else {
            System.out.println("登录失败。用户名或密码错误。");
        }
    }

    private void viewBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("没有找到任何书籍。");
        } else {
            for (Book book : books) {
                System.out.println(book); // 假设Book类重写了toString方法
            }
        }
    }

    private void searchBooks() {
        System.out.print("请输入书名或ISBN进行搜索: ");
        String keyword = scanner.nextLine();
        List<Book> results = bookService.searchBooks(keyword);
        if (results.isEmpty()) {
            System.out.println("没有找到匹配的书籍。");
        } else {
            for (Book book : results) {
                System.out.println(book); // 假设Book类重写了toString方法
            }
        }
    }


    public void showCategoryMenu() {
        System.out.println("请选择图书类别：");
        System.out.println("1. 计算机类");
        System.out.println("2. 医学类");
        System.out.println("3. 文学类");
        System.out.println("4. 法学类");
        System.out.println("5. 返回上一级");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                displayBooksByCategory("计算机类");
                break;
            case "2":
                displayBooksByCategory("医学类");
                break;
            case "3":
                displayBooksByCategory("文学类");
                break;
            case "4":
                displayBooksByCategory("法学类");
                break;
            case "5":
                showUserMenu();
                break;
            default:
                System.out.println("无效的选择，请重新输入。");
                showCategoryMenu();
        }
    }

    private void displayBooksByCategory(String category) {
        List<Book> books = bookService.getBooksByCategory(category);
        if (books.isEmpty()) {
            System.out.println("没有找到" + category + "的书籍。");
        } else {
            for (Book book : books) {
                System.out.println(book); // 假设Book类重写了toString方法
            }
        }
        showCategoryMenu(); // 显示类别菜单以允许进一步选择
    }

    private void addBook() {
        System.out.print("请输入书名: ");
        String title = scanner.nextLine();
        System.out.print("请输入作者: ");
        String author = scanner.nextLine();
        System.out.print("请输入ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("请输入类别: ");
        String category = scanner.nextLine();
        System.out.print("请输入出版日期（格式：yyyy-MM-dd）: ");
        String publishedDateStr = scanner.nextLine();

        boolean success = bookService.addBook(title, author, isbn, category, publishedDateStr);
        if (success) {
            System.out.println("书籍添加成功！");
        } else {
            System.out.println("书籍添加失败。");
        }
    }

    private void deleteBook() {
        System.out.print("请输入要删除的书籍ISBN: ");
        String isbn = scanner.nextLine();

        boolean success = bookService.deleteBook(isbn);
        if (success) {
            System.out.println("书籍删除成功！");
        } else {
            System.out.println("书籍删除失败。未找到对应ISBN的书籍。");
        }
    }

    private void logout() {
        currentUser = null;
        System.out.println("已注销登录。");
    }

    private void exit() {
        System.out.println("正在退出系统...");
        System.exit(0);
    }
}