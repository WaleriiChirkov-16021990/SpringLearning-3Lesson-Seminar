package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.service.BookService;
import ru.gb.springdemo.service.IssuerService;
import ru.gb.springdemo.service.ReaderService;

@Controller
@RequestMapping("/ui")
public class TemplateController {
    private final BookService bookService;
    private final IssuerService issuerService;
    private final ReaderService readerService;

    @Autowired
    public TemplateController(BookService bookService, IssuerService issuerService, ReaderService readerService) {
        this.bookService = bookService;
        this.issuerService = issuerService;
        this.readerService = readerService;
    }

    @GetMapping("/books")
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.getBookList());
        return "booksTemplate";
    }

    @GetMapping("/issues")
    public String getIssue(Model model) {
        model.addAttribute("issues",
                issuerService.getIssueList());
        return "issuesTemplate";
    }

    @GetMapping("/readers")
    public String getReader(Model model) {
        model.addAttribute("readers", readerService.getReaders());
        return "readerTemplate";
    }

    @GetMapping("/readers/{id}")
    public String getIssueByReaderId(@PathVariable("id") long id, Model model) {
        Reader reader = readerService.getReaderById(id);
        model.addAttribute("reader", reader);
        return "issueByReaderId";
    }
}
