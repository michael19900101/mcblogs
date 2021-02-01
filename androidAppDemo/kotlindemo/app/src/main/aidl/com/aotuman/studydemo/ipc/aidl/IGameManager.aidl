// IGameManager.aidl
package com.aotuman.studydemo.ipc.aidl;

// Declare any non-default types here with import statements
import com.aotuman.studydemo.ipc.aidl.IGame;
interface IGameManager {
  List<Game>getGameList();
  void addGame(in Game game);
}