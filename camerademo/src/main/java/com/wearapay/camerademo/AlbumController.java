/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.wearapay.camerademo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Desction:
 * Author:pengjianbo
 * Date:15/10/10 下午4:26
 */
public class AlbumController {
  private static final long MIN_SIZE = 1024L * 10;

  private Context context;
  private ContentResolver resolver;

  public AlbumController(Context context) {
    this.context = context;
    resolver = context.getContentResolver();
  }

  /** 获取最近照片列表 */
  public List<PhotoModel> getCurrent() {
    Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] {
        MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DATE_ADDED,
        MediaStore.Images.ImageColumns.SIZE
    }, null, null, MediaStore.Images.ImageColumns.DATE_ADDED);
    if (cursor == null || !cursor.moveToNext()) return new ArrayList<PhotoModel>();
    List<PhotoModel> photos = new ArrayList<PhotoModel>();
    cursor.moveToLast();
    do {
      if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) >= MIN_SIZE) {
        PhotoModel photoModel = new PhotoModel();
        photoModel.setOriginalPath(
            cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
        photos.add(photoModel);
      }
    } while (cursor.moveToPrevious());
    cursor.close();
    return photos;
  }

  /** 获取所有相册列表 */
  public List<AlbumModel> getAlbums() {
    List<AlbumModel> albums = new ArrayList<AlbumModel>();
    Map<String, AlbumModel> map = new HashMap<String, AlbumModel>();
    Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    String[] projection = new String[] {
        MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
        MediaStore.Images.ImageColumns.SIZE
    };
    Cursor cursor = resolver.query(uri, projection, null, null, null);
    if (cursor == null || !cursor.moveToNext()) return new ArrayList<AlbumModel>();
    cursor.moveToLast();
    AlbumModel current = new AlbumModel("全部照片", 0,
        cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)),
        true); // "最近照片"相册
    albums.add(current);
    do {
      if (cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) < MIN_SIZE) {
        continue;
      }

      current.increaseCount();
      String name = cursor.getString(
          cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
      if (map.keySet().contains(name)) {
        map.get(name).increaseCount();
      } else {
        AlbumModel album = new AlbumModel(name, 1,
            cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
        map.put(name, album);
        albums.add(album);
      }
    } while (cursor.moveToPrevious());
    cursor.close();
    return albums;
  }

  /** 获取对应相册下的照片 */
  public List<PhotoModel> getAlbum(String name) {
    if( ("全部照片").equals(name)) {
      return getCurrent();
    }
    Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] {
        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATA,
        MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.SIZE
    }, "bucket_display_name = ?", new String[] { name }, MediaStore.Images.ImageColumns.DATE_ADDED);
    if (cursor == null || !cursor.moveToNext()) return new ArrayList<PhotoModel>();
    List<PhotoModel> photos = new ArrayList<PhotoModel>();
    cursor.moveToLast();
    do {
      if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) >= MIN_SIZE) {
        PhotoModel photoModel = new PhotoModel();
        photoModel.setOriginalPath(
            cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
        photos.add(photoModel);
      }
    } while (cursor.moveToPrevious());
    cursor.close();
    return photos;
  }
}
