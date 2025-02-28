    package id.ac.ui.cs.advprog.eshop.controller;

    import id.ac.ui.cs.advprog.eshop.model.Car;
    import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import id.ac.ui.cs.advprog.eshop.model.Product;
    import id.ac.ui.cs.advprog.eshop.service.ProductService;

    import java.util.List;


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

    @Controller
    @RequestMapping("/car")
    class CarController extends ProductController {

        @Autowired
        private CarServiceImpl carService;

        @GetMapping("/createCar")
        public String createCarPage(Model model) {
            Car car = new Car();
            model.addAttribute("car", car);
            return "CreateCar";
        }

        @PostMapping("/createCar")
        public String createCarPost(@ModelAttribute("product") Car car, Model model) {
            carService.create(car);
            return "redirect:listCar";
        }

        @GetMapping("/listCar")
        public String carListPage(Model model) {
            List<Car> allCars = carService.findAll();
            model.addAttribute("cars", allCars);
            return "CarList";
        }

        @GetMapping(value="/editCar/{carId}")
        public String editCarPage(@PathVariable String carId, Model model) {
            Car car = carService.findById(carId);
            model.addAttribute("car", car);
            return "EditCar";
        }

        @PostMapping("/editCar")
        public String editCarPost(@ModelAttribute Car car, Model model) {
            System.out.println(car.getCarId());
            carService.update(car.getCarId(), car);
            return "redirect:listCar";
        }

        @PostMapping("/deleteCar")
        public String deleteCar(@RequestParam("carId") String carId) {
            carService.deleteCarById(carId);
            return "redirect:listCar";
        }

    }