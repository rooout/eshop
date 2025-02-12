    package id.ac.ui.cs.advprog.eshop.controller;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.ModelAttribute;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;

    import id.ac.ui.cs.advprog.eshop.model.Product;
    import id.ac.ui.cs.advprog.eshop.service.ProductService;


    @Controller
    @RequestMapping("/product")
    public class ProductController {
        @Autowired
        private ProductService productService;

        @GetMapping("/")
        public String getHome(Model model) {
            return "index";
        }

        @GetMapping("/create")
        public String createProduct(Model model) {
            Product product = new Product();
            model.addAttribute("product", product);
            return "createProduct";
        }

        @PostMapping("/create")
        public String createProduct(@ModelAttribute Product product, Model model) {
            productService.create(product);
            return "redirect:list";
        }

        @GetMapping("/list")
        public String listProduct(Model model) {
            model.addAttribute("products", productService.findAll());
            return "productList";
        }

        @GetMapping("/update/{id}")
        public String updateProduct(@PathVariable("id") String id, Model model) {
            Product product = productService.findById(id);
            if (product == null) {
                return "redirect:/product/list"; // Jika produk tidak ditemukan, kembali ke daftar
            }
            model.addAttribute("product", product);
            return "editProduct";
        }

        @PostMapping("/update/{id}")
        public String updateProduct(@PathVariable("id") String id, @ModelAttribute Product product, Model model) {
            product.setProductId(id);
            productService.update(product);
            return "redirect:/product/list";
        }

        @PostMapping("/delete/{id}")
        public String deleteProduct(@PathVariable("id") String id) {
            productService.delete(id);
            return "redirect:/product/list";
        }
    }