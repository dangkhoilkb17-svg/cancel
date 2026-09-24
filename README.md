# Baritone Emergency Stop — Fabric 1.21.1

Một chức năng duy nhất:

**F10 → dừng pathing/tác vụ của Baritone primary instance.**

Mod không thêm command, GUI, config, automation hay logic pathfinding riêng.

## Yêu cầu

- Minecraft 1.21.1
- Fabric Loader
- Fabric API 0.116.15+1.21.1
- Baritone 1.11.3 (Fabric, dành cho 1.21/1.21.1)
- Java 21

## Build

1. Cài JDK 21 và đặt file API chính thức `baritone-api-fabric-1.11.3.jar` vào thư mục `libs/`.
2. Chạy `gradlew.bat build` trên Windows hoặc `./gradlew build` trên macOS/Linux.
3. File mod nằm trong `build/libs/`.

Nếu không dùng Wrapper, có thể chạy `gradle build` với Gradle 8.10 hoặc mới hơn.

Build sẽ không hoàn tất nếu thiếu file API Baritone trong `libs/`, vì API này
không được tải tự động từ Maven trong project.

## Phím

Mặc định: **F10**.

Có thể đổi trong:
Options → Controls → Key Binds

## Cách dừng

Phím F10 duyệt mọi Baritone instance đang hoạt động và gọi trực tiếp:

`baritone.getPathingBehavior().cancelEverything();`

Theo public API Baritone 1.11.3, `cancelEverything()` hủy pathing, path
calculation và mọi process đang kiểm soát pathing của instance đó. Vì vậy
thao tác này dừng cả primary instance và các instance Baritone đang hoạt động,
không chỉ path hiện tại. Nếu Baritone chưa khởi tạo hoặc danh sách instance
đang rỗng, F10 được bỏ qua an toàn. Không gửi chat command.
