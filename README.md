# MoonGems
## 明月宝石——全新附魔宝石
支持版本:1.9 - 1.12.2  
基于Paper开发  
[Download]("https://github.com/MoonDrinkWind/MoonGems/releases")
## 食用方法
提示:  
英文ID可在 [ID List]("https://minecraft-ids.grahamedgecombe.com") 查询
#### 玩家使用方法
玩家下蹲状态右键铁砧打开附魔GUI界面  
![GUI界面]("https://sm.ms/image/G8OLnBouJ1ywmt7")  
左边放上需要附魔的物品  
右边放上附魔石  
再点击中间的玻璃  
即可附魔（有概率）
#### 服主配置方法
##### config.yml
附魔英文ID可在 [附魔 WIKI]("https://minecraft-zh.gamepedia.com/%E9%99%84%E9%AD%94") 中查询
```
'&f一级锋利宝石': # 附魔石名称
  Enchant: 'DAMAGE_ALL' # 附魔石所带附魔 
  level: 1 # 附魔石所带附魔等级
  SuccessRate: 50 # 附魔概率
  Malleable: # 可附魔物品(英文ID)
    - COAL
    - DIAMOND_SWORD
'&f二级锋利宝石':
  Enchant: 'DAMAGE_ALL'
  level: 2
  SuccessRate: 60
  Malleable:
    - COAL
```
##### message.yml
```
成功附魔: '&7&l[&f&lMoonEGems&7&l] 成功为你的武器附魔上宝石!' # 成功附魔提示
失败附魔: '&7&l[&f&lMoonEGems&7&l] &d附魔失败!' # 失败附魔提示
界面标题: '&7&l[ &8&l附魔武器 &7&l]' # 附魔GUI界面标题
```
##### drop.yml
生物随机掉落一种附魔石
生物ID: 掉落附魔石概率(填0则百分之零 填50则百分之五十 填100则百分之百 以此类推 最大为100)  
生物ID可在 [生物ID WIKI]("https://minecraft-zh.gamepedia.com/Java版数据值/扁平化前/实体ID") 中查询  
建议删除初始内容根据自己需要调整
```
ELDER_GUARDIAN: 0 
WITHER_SKELETON: 0 
STRAY: 0 
HUSK: 0 
ZOMBIE_VILLAGER: 0 
SKELETON_HORSE: 0 
ZOMBIE_HORSE: 0 
ARMOR_STAND: 0 
DONKEY: 0  
MULE: 0 
EVOKER: 0 
VEX: 0 
VINDICATOR: 0 
ILLUSIONER: 0
CREEPER: 0
SKELETON: 0
SPIDER: 0
GIANT: 0
ZOMBIE: 0
SLIME: 0
GHAST: 0
PIG_ZOMBIE: 0
ENDERMAN: 0
CAVE_SPIDER: 0
SILVERFISH: 0
BLAZE: 0
MAGMA_CUBE: 0
ENDER_DRAGON: 0
WITHER: 0
BAT: 0
WITCH: 0
ENDERMITE: 0
GUARDIAN: 0
SHULKER: 0
PIG: 100
SHEEP: 0
COW: 0
CHICKEN: 0
SQUID: 0
WOLF: 0
MUSHROOM_COW: 0
SNOWMAN: 0
OCELOT: 0
IRON_GOLEM: 0
HORSE: 0
RABBIT: 0
POLAR_BEAR: 0
LLAMA: 0
PARROT: 0
VILLAGER: 0
PLAYER: 0
```

