package Spring.LoginRegister.Student;

import Spring.LoginRegister.Entity.Student;
import Spring.LoginRegister.House.HouseRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private final HouseRepository houseRepository;
    private static final long MAX_IMAGE_SIZE_BYTES = 300 * 1024; // 300 KB in bytes

    public StudentServiceImpl(StudentRepository studentRepository,
                              HouseRepository houseRepository) {
        this.studentRepository = studentRepository;
        this.houseRepository = houseRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public String findStudentHouse(String studentID) {
        return studentRepository.findStudentHouse(studentID);
    }

    @Override
    public String findStudentHouseBYID(int studentID) {
        return studentRepository.findStudentHouseBYID(studentID);
    }

    @Override
    public List<Student> findByStudentNameContainingIgnoreCase(int masterid, String keyword) {
        return studentRepository.findByStudentNameContainingIgnoreCase(masterid, keyword);
    }

    @Override
    public List<Student> findMasterStudentsMatchesHouse(int masterid) {
        return studentRepository.findMasterStudentsMatchesHouse(masterid);
    }

    @Override
    public String findStudentNumber(String studentNumber) {
        return studentRepository.findStudentByStudentNumber(studentNumber);
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(int studentid) {
        return studentRepository.findById(studentid).get();
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(int studentid) {
        studentRepository.deleteById(studentid);
    }

    @Override
    public Student findByStudentNumber(String studentNumber) {
        return studentRepository.findBystudentNumber(studentNumber);
    }

    @Override
    public List<Student> findByStatusLike(String status) {
        return null;
    }

    public List<Object> getContacts() {
        List<Object> contacts = studentRepository.getAllContacts();
        return contacts;
    }

    @Override
    public void updateImage(String studentNumber, MultipartFile image) throws IOException {


        String uploadDir = "uploads";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }
        // Set the maximum image size to 300 KB

        Student student = studentRepository.findBystudentNumber(studentNumber);
        String existingImagePath = student.getImagePath(); // get the existing image path
        if (existingImagePath != null) {
            Path existingFilePath = Paths.get(existingImagePath);
            Files.deleteIfExists(existingFilePath); // delete the existing file if it exists
        }
        if (student != null) {

// Save the file to the uploads directory
            String fileName = StringUtils.cleanPath(student.getStudentNumber() + "_" + UUID.randomUUID().toString() + "." + image.getOriginalFilename());
            Path filePath = uploadPath.resolve(fileName);

// Check if the image size exceeds the maximum allowed size
            long fileSizeInBytes = image.getSize();
            System.out.println(fileSizeInBytes);
            if (fileSizeInBytes > MAX_IMAGE_SIZE_BYTES) {
                // Save the original image to the uploads directory
                Path originalFilePath = uploadPath.resolve(student.getStudentNumber() + "_" + UUID.randomUUID().toString() + ".jpg");
                Files.copy(image.getInputStream(), originalFilePath, StandardCopyOption.REPLACE_EXISTING);

                // Create a thumbnail of the image and save it to the uploads directory
                Path thumbnailFilePath = uploadPath.resolve(student.getStudentNumber() + "_" + UUID.randomUUID().toString() + ".jpg");
                Thumbnails.of(originalFilePath.toFile()).size(800, 800).outputQuality(0.9).toFile(thumbnailFilePath.toFile());

                System.out.println(thumbnailFilePath);
                // Replace the original image with the thumbnail
                Files.delete(originalFilePath);
                Files.move(thumbnailFilePath, filePath);

            } else {
                // Save the original image to the uploads directory
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }

// Update the user's image path in the database

            student.setImagePath(filePath.toString());

            studentRepository.save(student);
        }


    }

    @Override
    public void FullStudentInfo(int studentID, Student student) throws IOException {


        Student ExistingStudent = studentRepository.findById(studentID).get();
        ExistingStudent.setStudentNumber(student.getStudentNumber());
        ExistingStudent.setStudentName(student.getStudentName());
        ExistingStudent.setSex(student.getSex());
        ExistingStudent.setStudentClass(student.getStudentClass());
        ExistingStudent.setHouse(student.getHouse());
        ExistingStudent.setParentContact(student.getParentContact());


        studentRepository.save(ExistingStudent);


    }

    @Override
    public void updateImageFull(int studentID, MultipartFile image) throws IOException {


        String uploadDir = "uploads";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }
        // Set the maximum image size to 300 KB

        Student student = studentRepository.findById(studentID).get();
        String existingImagePath = student.getImagePath(); // get the existing image path
        if (image != null && !image.isEmpty()) {
            if (existingImagePath != null) {
                Path existingFilePath = Paths.get(existingImagePath);
                Files.deleteIfExists(existingFilePath); // delete the existing file if it exists
            }
            if (student != null) {
// Convert the image to JPEG format if it's not already in that format

// Save the file to the uploads directory
                String fileName = StringUtils.cleanPath(student.getStudentNumber() + "_" + UUID.randomUUID().toString() + "." + image.getOriginalFilename());
                Path filePath = uploadPath.resolve(fileName);

// Check if the image size exceeds the maximum allowed size
                long fileSizeInBytes = image.getSize();
                System.out.println(fileSizeInBytes);
                if (fileSizeInBytes > MAX_IMAGE_SIZE_BYTES) {
                    // Save the original image to the uploads directory
                    Path originalFilePath = uploadPath.resolve(student.getStudentNumber() + "_" + UUID.randomUUID().toString() + ".jpg");
                    Files.copy(image.getInputStream(), originalFilePath, StandardCopyOption.REPLACE_EXISTING);

                    // Create a thumbnail of the image and save it to the uploads directory
                    Path thumbnailFilePath = uploadPath.resolve(student.getStudentNumber() + "_" + UUID.randomUUID().toString() + ".jpg");
                    Thumbnails.of(originalFilePath.toFile()).size(800, 800).outputQuality(0.9).toFile(thumbnailFilePath.toFile());

                    System.out.println(thumbnailFilePath);
                    // Replace the original image with the thumbnail
                    Files.delete(originalFilePath);
                    Files.move(thumbnailFilePath, filePath);

                } else {
                    // Save the original image to the uploads directory
                    Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                }

// Update the user's image path in the database

                student.setImagePath(filePath.toString());
            }
            studentRepository.save(student);
        }

    }

    @Override
    public void deleteUnlinkedImages() throws IOException {
        String uploadDir = "uploads";
        Path uploadPath = Paths.get(uploadDir);

        // Step 1: Get a list of all image files in the "uploads" directory
        // Get a list of all image paths in the uploads directory
        List<String> allImagePaths = Files.walk(Paths.get("uploads"))
                .filter(p -> !Files.isDirectory(p))
                .map(Path::toString)
                .collect(Collectors.toList());

// Get a list of all image paths that are linked to a student
        List<String> linkedImagePaths = studentRepository.findAll().stream()
                .map(Student::getImagePath)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

// Get a list of all image paths that are not linked to a student
        List<String> unlinkedImagePaths = allImagePaths.stream()
                .filter(path -> !linkedImagePaths.contains(path))
                .collect(Collectors.toList());

// Delete all images that are not linked to a student
        for (String imagePath : unlinkedImagePaths) {
            Files.deleteIfExists(Paths.get(imagePath));
        }

    }

}