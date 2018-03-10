package amediaplayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Slider vSlider;
    
    @FXML
    private Slider seekSlider;
    
    private String filepath;

    private MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;

    @FXML
    private void handleButtonAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a Media File", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filepath = file.toURI().toString();

        if (filepath != null) {
            Media media = new Media(filepath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
                DoubleProperty width = mediaView.fitWidthProperty();
                DoubleProperty height = mediaView.fitHeightProperty();
                width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                
            vSlider.setValue(mediaPlayer.getVolume() * 100);
            vSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(vSlider.getValue()/100);
                }
            });
                 
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    seekSlider.setValue(newValue.toSeconds());
                }
            });
            
            seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
                }
            });

            mediaPlayer.play();
        }

    }

    @FXML
    private void stopVideo(ActionEvent event) {
        mediaPlayer.stop();
    }

    @FXML
    private void playVideo(ActionEvent event) {
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }

    @FXML
    private void pauseVideo(ActionEvent event) {
        mediaPlayer.pause();
    }

    @FXML
    private void sVideo(ActionEvent event) {
        mediaPlayer.setRate(0.75);
    }

    @FXML
    private void ssVideo(ActionEvent event) {
        mediaPlayer.setRate(0.5);
    }

    @FXML
    private void fVideo(ActionEvent event) {
        mediaPlayer.setRate(1.25);
    }

    @FXML
    private void ffVideo(ActionEvent event) {
        mediaPlayer.setRate(1.5);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
