/*
 * @ (#) DataInit.java     1.0    7/7/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */
package vantruong.iuh.config;


/*
 * @description
 * @author:NguyenTruong
 * @date:  7/7/2026
 * @version:    1.0
 */

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vantruong.iuh.entity.*;
import vantruong.iuh.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

    private static final String RAW_PASSWORD = "123456";

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ReviewRepository reviewRepository;
    private final ProductImageRepository productImageRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Override
    @Transactional
    public void run(String... args) {
        Map<RoleName, Role> roles = seedRoles();
        Map<String, Brand> brands = seedBrands();
        Map<String, Category> categories = seedCategories();
        Map<String, Product> products = seedProducts(brands, categories);
        Map<String, User> users = seedUsers(roles);

        seedCarts(users, products);
        seedOrders(users, products);
        seedReviews(users, products);
        seedProductImages(products);
    }

    private Map<RoleName, Role> seedRoles() {
        Map<RoleName, Role> roles = new LinkedHashMap<>();
        for (RoleName roleName : RoleName.values()) {
            roles.put(roleName, roleRepository.findByName(roleName).orElseGet(() -> {
                Role role = new Role();
                role.setName(roleName);
                return roleRepository.save(role);
            }));
        }
        return roles;
    }

    private Map<String, Brand> seedBrands() {
        Map<String, Brand> brands = new LinkedHashMap<>();
        brands.put("Dell", ensureBrand("Dell", "Laptop doanh nghiệp và cá nhân bền bỉ, hiệu năng ổn định."));
        brands.put("HP", ensureBrand("HP", "Dòng máy đa dạng cho học tập, văn phòng và giải trí."));
        brands.put("ASUS", ensureBrand("ASUS", "Laptop mỏng nhẹ và gaming với thiết kế hiện đại."));
        brands.put("Lenovo", ensureBrand("Lenovo", "Máy tính xách tay tối ưu cho công việc và học tập."));
        brands.put("Acer", ensureBrand("Acer", "Laptop phổ thông và gaming với giá hợp lý."));
        return brands;
    }

    private Brand ensureBrand(String name, String description) {
        return brandRepository.findAll().stream()
                .filter(brand -> name.equalsIgnoreCase(brand.getName()))
                .findFirst()
                .orElseGet(() -> {
                    Brand brand = new Brand();
                    brand.setName(name);
                    brand.setDescription(description);
                    return brandRepository.save(brand);
                });
    }

    private Map<String, Category> seedCategories() {
        Map<String, Category> categories = new LinkedHashMap<>();
        categories.put("Ultrabook", ensureCategory("Ultrabook", "Laptop mỏng nhẹ, pin tốt, phù hợp di chuyển."));
        categories.put("Gaming", ensureCategory("Gaming", "Laptop hiệu năng cao dành cho chơi game và đồ họa."));
        categories.put("Office", ensureCategory("Office", "Laptop cho công việc văn phòng, học tập và lập trình."));
        return categories;
    }

    private Category ensureCategory(String name, String description) {
        return categoryRepository.findAll().stream()
                .filter(category -> name.equalsIgnoreCase(category.getName()))
                .findFirst()
                .orElseGet(() -> {
                    Category category = new Category();
                    category.setName(name);
                    category.setDescription(description);
                    return categoryRepository.save(category);
                });
    }

    private Map<String, Product> seedProducts(Map<String, Brand> brands, Map<String, Category> categories) {
        Map<String, Product> products = new LinkedHashMap<>();

        products.put("Dell XPS 13 9340", ensureProduct(
                "Dell XPS 13 9340",
                "Laptop siêu mỏng nhẹ với thiết kế cao cấp, phù hợp di chuyển hằng ngày.",
                new BigDecimal("32990000"),
                12,
                "Intel Core Ultra 7",
                "16GB LPDDR5X",
                "1TB SSD",
                "13.4 inch QHD+",
                "/images/products/dell-xps-13.jpg",
                brands.get("Dell"),
                categories.get("Ultrabook")
        ));

        products.put("ASUS ROG Strix G16", ensureProduct(
                "ASUS ROG Strix G16",
                "Laptop gaming mạnh mẽ cho trải nghiệm chiến game mượt mà và ổn định.",
                new BigDecimal("42990000"),
                8,
                "Intel Core i9-14900HX",
                "32GB DDR5",
                "1TB SSD",
                "16 inch 2.5K 165Hz",
                "/images/products/asus-rog-strix-g16.jpg",
                brands.get("ASUS"),
                categories.get("Gaming")
        ));

        products.put("Lenovo IdeaPad Slim 5", ensureProduct(
                "Lenovo IdeaPad Slim 5",
                "Laptop văn phòng mỏng nhẹ, pin tốt, phù hợp học tập và làm việc.",
                new BigDecimal("18990000"),
                20,
                "AMD Ryzen 7 8845HS",
                "16GB DDR5",
                "512GB SSD",
                "14 inch WUXGA",
                "/images/products/lenovo-ideapad-slim-5.jpg",
                brands.get("Lenovo"),
                categories.get("Office")
        ));

        products.put("HP Pavilion 15", ensureProduct(
                "HP Pavilion 15",
                "Mẫu laptop cân bằng giữa hiệu năng, pin và chi phí cho người dùng phổ thông.",
                new BigDecimal("17990000"),
                15,
                "Intel Core i5-1335U",
                "16GB DDR4",
                "512GB SSD",
                "15.6 inch FHD",
                "/images/products/hp-pavilion-15.jpg",
                brands.get("HP"),
                categories.get("Office")
        ));

        products.put("Acer Nitro 5", ensureProduct(
                "Acer Nitro 5",
                "Laptop gaming phổ biến với cấu hình mạnh và tản nhiệt tốt.",
                new BigDecimal("24990000"),
                10,
                "Intel Core i7-13620H",
                "16GB DDR5",
                "1TB SSD",
                "15.6 inch FHD 144Hz",
                "/images/products/acer-nitro-5.jpg",
                brands.get("Acer"),
                categories.get("Gaming")
        ));

        products.put("Dell Latitude 5440", ensureProduct(
                "Dell Latitude 5440",
                "Dòng laptop doanh nghiệp bền bỉ, bảo mật tốt và dễ nâng cấp.",
                new BigDecimal("25990000"),
                9,
                "Intel Core i5-1345U",
                "16GB DDR4",
                "512GB SSD",
                "14 inch FHD",
                "/images/products/dell-latitude-5440.jpg",
                brands.get("Dell"),
                categories.get("Office")
        ));

        return products;
    }

    private Product ensureProduct(String name,
                                  String description,
                                  BigDecimal price,
                                  Integer quantity,
                                  String cpu,
                                  String ram,
                                  String storage,
                                  String screenSize,
                                  String thumbnail,
                                  Brand brand,
                                  Category category) {
        Product product = productRepository.findAll().stream()
                .filter(item -> name.equalsIgnoreCase(item.getName()))
                .findFirst()
                .orElseGet(Product::new);

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCpu(cpu);
        product.setRam(ram);
        product.setStorage(storage);
        product.setScreenSize(screenSize);
        product.setThumbnail(thumbnail);
        product.setBrand(brand);
        product.setCategory(category);

        return productRepository.save(product);
    }

    private Map<String, User> seedUsers(Map<RoleName, Role> roles) {
        Map<String, User> users = new LinkedHashMap<>();

        users.put("admin", ensureUser(
                "admin",
                RAW_PASSWORD,
                "admin@quanlisanpham.com",
                "0900000001",
                "Hà Nội",
                Set.of(roles.get(RoleName.ROLE_ADMIN), roles.get(RoleName.ROLE_STAFF))
        ));

        users.put("staff", ensureUser(
                "staff",
                RAW_PASSWORD,
                "staff@quanlisanpham.com",
                "0900000002",
                "Đà Nẵng",
                Set.of(roles.get(RoleName.ROLE_STAFF))
        ));

        users.put("an", ensureUser(
                "an",
                RAW_PASSWORD,
                "an@quanlisanpham.com",
                "0900000003",
                "TP. Hồ Chí Minh",
                Set.of(roles.get(RoleName.ROLE_CUSTOMER))
        ));

        users.put("binh", ensureUser(
                "binh",
                RAW_PASSWORD,
                "binh@quanlisanpham.com",
                "0900000004",
                "Cần Thơ",
                Set.of(roles.get(RoleName.ROLE_CUSTOMER))
        ));

        return users;
    }

    private User ensureUser(String username,
                            String rawPassword,
                            String email,
                            String phone,
                            String address,
                            Set<Role> roles) {
        User user = userRepository.findByUsername(username).orElseGet(User::new);
        boolean isNew = user.getId() == null;

        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRoles(new HashSet<>(roles));
        if (isNew || user.getPassword() == null || user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        }

        if (user.getCart() == null) {
            Cart cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
        }

        return userRepository.save(user);
    }

    private void seedCarts(Map<String, User> users, Map<String, Product> products) {
        createCartItem(users.get("an"), products.get("Dell XPS 13 9340"), 1);
        createCartItem(users.get("an"), products.get("Lenovo IdeaPad Slim 5"), 1);

        createCartItem(users.get("binh"), products.get("ASUS ROG Strix G16"), 1);
        createCartItem(users.get("binh"), products.get("Acer Nitro 5"), 1);
    }

    private void createCartItem(User user, Product product, int quantity) {
        Cart cart = cartRepository.findByUserId(user.getId()).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            user.setCart(newCart);
            return cartRepository.save(newCart);
        });

        if (cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId()).isPresent()) {
            return;
        }

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);
        cart.getItems().add(item);
        cartItemRepository.save(item);
        cartRepository.save(cart);
    }

    private void seedOrders(Map<String, User> users, Map<String, Product> products) {
        createOrderIfMissing(
                users.get("an"),
                OrderStatus.COMPLETED,
                "TP. Hồ Chí Minh",
                "COD",
                List.of(
                        new OrderLine(products.get("Lenovo IdeaPad Slim 5"), 1, new BigDecimal("18990000")),
                        new OrderLine(products.get("HP Pavilion 15"), 1, new BigDecimal("17990000"))
                )
        );

        createOrderIfMissing(
                users.get("binh"),
                OrderStatus.PENDING,
                "Cần Thơ",
                "VNPAY",
                List.of(
                        new OrderLine(products.get("ASUS ROG Strix G16"), 1, new BigDecimal("42990000")),
                        new OrderLine(products.get("Acer Nitro 5"), 1, new BigDecimal("24990000"))
                )
        );
    }

    private void createOrderIfMissing(User user,
                                      OrderStatus status,
                                      String shippingAddress,
                                      String paymentMethod,
                                      List<OrderLine> lines) {
        if (!orderRepository.findByUserId(user.getId()).isEmpty()) {
            return;
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now().minusDays(1));
        order.setStatus(status);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderLine line : lines) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(line.product());
            detail.setQuantity(line.quantity());
            detail.setPrice(line.price());
            order.getDetails().add(detail);
            totalAmount = totalAmount.add(line.price().multiply(BigDecimal.valueOf(line.quantity())));
        }
        order.setTotalAmount(totalAmount);

        user.getOrders().add(order);
        orderRepository.save(order);
    }

    private void seedReviews(Map<String, User> users, Map<String, Product> products) {
        createReviewIfMissing(users.get("an"), products.get("Dell XPS 13 9340"), 5, "Thiết kế mỏng nhẹ, pin tốt và rất phù hợp đi học/đi làm.");
        createReviewIfMissing(users.get("binh"), products.get("ASUS ROG Strix G16"), 5, "Hiệu năng mạnh, chơi game mượt và màn hình rất đã.");
        createReviewIfMissing(users.get("an"), products.get("Lenovo IdeaPad Slim 5"), 4, "Máy gọn, chạy ổn định, đáng tiền trong tầm giá.");
    }

    private void createReviewIfMissing(User user, Product product, Integer rating, String comment) {
        if (!reviewRepository.findByProductId(product.getId()).isEmpty()) {
            return;
        }

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);
        reviewRepository.save(review);
    }

    private void seedProductImages(Map<String, Product> products) {
        addImagesIfMissing(products.get("Dell XPS 13 9340"), List.of(
                "/images/products/dell-xps-13-1.jpg",
                "/images/products/dell-xps-13-2.jpg"
        ));

        addImagesIfMissing(products.get("ASUS ROG Strix G16"), List.of(
                "/images/products/asus-rog-strix-g16-1.jpg",
                "/images/products/asus-rog-strix-g16-2.jpg"
        ));

        addImagesIfMissing(products.get("Lenovo IdeaPad Slim 5"), List.of(
                "/images/products/lenovo-ideapad-slim-5-1.jpg",
                "/images/products/lenovo-ideapad-slim-5-2.jpg"
        ));
    }

    private void addImagesIfMissing(Product product, List<String> imageUrls) {
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            return;
        }

        for (String imageUrl : imageUrls) {
            ProductImage image = new ProductImage();
            image.setImageUrl(imageUrl);
            image.setProduct(product);
            product.getImages().add(image);
            productImageRepository.save(image);
        }

        productRepository.save(product);
    }

    private record OrderLine(Product product, int quantity, BigDecimal price) {
    }
}
