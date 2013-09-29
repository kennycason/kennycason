
CREATE TABLE IF NOT EXISTS `lang_en` (
  `textid` varchar(255) collate latin1_general_ci NOT NULL,
  `text` longtext charset utf8 collate utf8_unicode_ci NOT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `lang_jp` (
  `textid` varchar(255) collate latin1_general_ci NOT NULL,
  `text` longtextNOT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `lang_zh` (
  `textid` varchar(255) collate latin1_general_ci NOT NULL,
  `text` longtext charset utf8 collate utf8_unicode_ci NOT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT INTO `lang_en` (`textid`,`text`) VALUES ('FNAME','First Name'),('LNAME','Last Name'),('SUBMIT','Submit'),('LANG_EN','English'),('LANG_JP','Japanese'),('LANG_ZH','Chinese');
INSERT INTO `lang_jp` (`textid`,`text`) VALUES ('FNAME','名'),('LNAME','姓'),('SUBMIT','確認'),('LANG_EN','英語'),('LANG_JP','日本語'),('LANG_ZH','中国語');
INSERT INTO `lang_zh` (`textid`,`text`) VALUES ('FNAME','名'),('LNAME','姓'),('SUBMIT','确定'),('LANG_EN','英语'),('LANG_JP','日语'),('LANG_ZH','中文简体');


