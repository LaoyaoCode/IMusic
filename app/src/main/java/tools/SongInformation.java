package tools;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by Laoyao on 2017/9/24.
 */
public class SongInformation
{
    private  static List<Information> TotalSongs = null ;

    private Context GiveContext = null ;

    public  SongInformation(Context give)
    {
        GiveContext = give ;
    }

    public static List<Information> GetTotalSongs()
    {
        return TotalSongs ;
    }

    /**
     * 获得数据库中所有的信息
     */
    public  void GetTotalInformation()
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
                        MediaStore.Audio.Media.ALBUM_ID,// 专辑名称ID
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
                        MediaStore.Audio.Media.ALBUM_ID,// 专辑名称ID
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

                /*
                try
                {
                    int value = Integer.parseInt(sdCursor.getString(6)) ;
                    song.setHadAlbumID(true);
                    // 专辑id
                    song.setAlbumID(value);
                }
                catch (Exception e)
                {
                    song.setHadAlbumID(false);
                    // 专辑id
                    song.setAlbumID(0);
                }*/
                // 专辑id
                song.setAlbumID(Integer.parseInt(sdCursor.getString(6)));

                // 年代
                if (sdCursor.getString(7) != null) {
                    song.setYear(sdCursor.getString(7));
                } else {
                    song.setYear("Unknown");
                }
                // 歌曲格式
                if ("audio/mpeg".equals(sdCursor.getString(8).trim())) {
                    song.setType("mp3");
                } else if ("audio/x-ms-wma".equals(sdCursor.getString(8).trim())) {
                    song.setType("wma");
                }
                // 文件大小
                if (sdCursor.getString(9) != null) {
                    float size = sdCursor.getInt(9) / 1024f / 1024f;
                    song.setSize((size + "").substring(0, 4) + "M");
                } else {
                    song.setSize("未知");
                }
                // 文件路径
                if (sdCursor.getString(10) != null) {
                    song.setFileUrl(sdCursor.getString(10));
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

                /*
                try
                {
                    int value = Integer.parseInt(insideCursor.getString(6)) ;
                    song.setHadAlbumID(true);
                    // 专辑id
                    song.setAlbumID(value);
                }
                catch (Exception e)
                {
                    song.setHadAlbumID(false);
                    // 专辑id
                    song.setAlbumID(0);
                }*/
                song.setAlbumID(Integer.parseInt(insideCursor.getString(6)));
                // 年代
                if (insideCursor.getString(7) != null) {
                    song.setYear(insideCursor.getString(7));
                } else {
                    song.setYear("Unknown");
                }
                // 歌曲格式
                if ("audio/mpeg".equals(insideCursor.getString(8).trim())) {
                    song.setType("mp3");
                } else if ("audio/x-ms-wma".equals(insideCursor.getString(8).trim())) {
                    song.setType("wma");
                }
                // 文件大小
                if (insideCursor.getString(9) != null) {
                    float size = insideCursor.getInt(9) / 1024f / 1024f;
                    song.setSize((size + "").substring(0, 4) + "M");
                } else {
                    song.setSize("未知");
                }
                // 文件路径
                if (insideCursor.getString(10) != null) {
                    song.setFileUrl(insideCursor.getString(10));
                }

                informations.add(song);
            } while (insideCursor.moveToNext());

            insideCursor.close();
        }

        TotalSongs =  informations ;
    }


    /**
     * 获取音乐的专辑图片地址 , test success , laoyao , 2017.9.24
     * @param album_id 专辑id号
     * @return 图片地址
     */
    public String GetAlbumImagePath(int album_id)
    {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[] { "album_art" };
        Cursor cur = GiveContext.getContentResolver().query(Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),
                projection, null, null, null);
        String album_art = null;

        if (cur.getCount() > 0 && cur.getColumnCount() > 0)
        {  cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        cur = null;
        return album_art;
    }

    /**
     * 主动刷新媒体库
     */
    public void RefreshMediaStore()
    {
        MediaScannerConnection.scanFile(GiveContext, new String[] { Environment
                .getRootDirectory().getAbsolutePath() , Environment
                .getExternalStorageDirectory().getAbsolutePath()  }, null, null);
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
        private int AlbumID ;
        //private boolean HadAlbumID = false ;

        public  Information()
        {

        }

        public Information(int id , String fileName, String title, int duration, String singer, String album, String year, String type, String size, String fileUrl) {
            FileName = fileName;
            Title = title;
            Duration = duration;
            Singer = singer;
            Album = album;
            Year = year;
            Type = type;
            Size = size;
            FileUrl = fileUrl;
            AlbumID = id ;
        }

        /*
        public boolean isHadAlbumID() {
            return HadAlbumID;
        }

        public void setHadAlbumID(boolean hadAlbumID) {
            HadAlbumID = hadAlbumID;
        }*/

        public int getAlbumID() {
            return AlbumID;
        }

        public void setAlbumID(int albumID) {
            AlbumID = albumID;
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
