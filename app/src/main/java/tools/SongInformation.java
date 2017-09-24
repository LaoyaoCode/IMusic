package tools;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laoyao on 2017/9/24.
 */

public class SongInformation
{
    private Context GiveContext = null ;

    public  SongInformation(Context give)
    {
        GiveContext = give ;
    }

    //test success , 2017.9.24
    public List<Information> GetTotalInformation()
    {
        List<Information> informations = new ArrayList<>() ;
        ContentResolver resolver = GiveContext.getContentResolver() ;

        Cursor sdCursor = resolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.YEAR,
                        MediaStore.Audio.Media.MIME_TYPE,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DATA },
                MediaStore.Audio.Media.MIME_TYPE + "=? or "
                        + MediaStore.Audio.Media.MIME_TYPE + "=?",
                new String[] { "audio/mpeg", "audio/x-ms-wma" }, null) ;

        Cursor insideCursor = resolver.query(
                MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.YEAR,
                        MediaStore.Audio.Media.MIME_TYPE,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DATA },
                MediaStore.Audio.Media.MIME_TYPE + "=? or "
                        + MediaStore.Audio.Media.MIME_TYPE + "=?",
                new String[] { "audio/mpeg", "audio/x-ms-wma" }, null) ;

        //获取外部所有的音乐
        if (sdCursor.moveToFirst()) {

            Information song = null;

            do {
                song = new Information();
                // 文件名
                song.setFileName(sdCursor.getString(1));
                // 歌曲名
                song.setTitle(sdCursor.getString(2));
                // 时长
                song.setDuration(sdCursor.getInt(3));
                // 歌手名
                song.setSinger(sdCursor.getString(4));
                // 专辑名
                song.setAlbum(sdCursor.getString(5));
                // 年代
                if (sdCursor.getString(6) != null) {
                    song.setYear(sdCursor.getString(6));
                } else {
                    song.setYear("Unknown");
                }
                // 歌曲格式
                if ("audio/mpeg".equals(sdCursor.getString(7).trim())) {
                    song.setType("mp3");
                } else if ("audio/x-ms-wma".equals(sdCursor.getString(7).trim())) {
                    song.setType("wma");
                }
                // 文件大小
                if (sdCursor.getString(8) != null) {
                    float size = sdCursor.getInt(8) / 1024f / 1024f;
                    song.setSize((size + "").substring(0, 4) + "M");
                } else {
                    song.setSize("未知");
                }
                // 文件路径
                if (sdCursor.getString(9) != null) {
                    song.setFileUrl(sdCursor.getString(9));
                }

                informations.add(song);

            } while (sdCursor.moveToNext());

            sdCursor.close();
        }

        //获取内部的所有音乐
        if (insideCursor.moveToFirst()) {

            Information song = null;

            do {
                song = new Information();
                // 文件名
                song.setFileName(insideCursor.getString(1));
                // 歌曲名
                song.setTitle(insideCursor.getString(2));
                // 时长
                song.setDuration(insideCursor.getInt(3));
                // 歌手名
                song.setSinger(insideCursor.getString(4));
                // 专辑名
                song.setAlbum(insideCursor.getString(5));
                // 年代
                if (insideCursor.getString(6) != null) {
                    song.setYear(insideCursor.getString(6));
                } else {
                    song.setYear("Unknown");
                }
                // 歌曲格式
                if ("audio/mpeg".equals(insideCursor.getString(7).trim())) {
                    song.setType("mp3");
                } else if ("audio/x-ms-wma".equals(insideCursor.getString(7).trim())) {
                    song.setType("wma");
                }
                // 文件大小
                if (insideCursor.getString(8) != null) {
                    float size = insideCursor.getInt(8) / 1024f / 1024f;
                    song.setSize((size + "").substring(0, 4) + "M");
                } else {
                    song.setSize("未知");
                }
                // 文件路径
                if (insideCursor.getString(9) != null) {
                    song.setFileUrl(insideCursor.getString(9));
                }

                informations.add(song);
            } while (insideCursor.moveToNext());

            insideCursor.close();
        }

        return informations ;
    }

    public static class Information
    {
        private String FileName;
        private String Title;
        private int Duration;
        private String Singer;
        private String Album;
        private String Year;
        private String Type;
        private String Size;
        private String FileUrl;

        public  Information()
        {

        }

        public Information(String fileName, String title, int duration, String singer, String album, String year, String type, String size, String fileUrl) {
            FileName = fileName;
            Title = title;
            Duration = duration;
            Singer = singer;
            Album = album;
            Year = year;
            Type = type;
            Size = size;
            FileUrl = fileUrl;
        }

        public String getFileName() {
            return FileName;
        }

        public String getTitle() {
            return Title;
        }

        public int getDuration() {
            return Duration;
        }

        public String getSinger() {
            return Singer;
        }

        public String getAlbum() {
            return Album;
        }

        public String getYear() {
            return Year;
        }

        public String getType() {
            return Type;
        }

        public String getSize() {
            return Size;
        }

        public String getFileUrl() {
            return FileUrl;
        }

        public void setFileName(String fileName) {
            FileName = fileName;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public void setDuration(int duration) {
            Duration = duration;
        }

        public void setSinger(String singer) {
            Singer = singer;
        }

        public void setAlbum(String album) {
            Album = album;
        }

        public void setYear(String year) {
            Year = year;
        }

        public void setType(String type) {
            Type = type;
        }

        public void setSize(String size) {
            Size = size;
        }

        public void setFileUrl(String fileUrl) {
            FileUrl = fileUrl;
        }
    }

}
