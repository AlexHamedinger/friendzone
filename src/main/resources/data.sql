--Insert into User
insert into user(userid,email,username,password,initialregistration,latestregistration)
         values (2806L,'alexpunkthamedinger@web.de','Alex','$2a$15$ooRhg7oqr4O5jCkA6U2dpO.2x71.OtBjxjqRVz6/10E0nCeGDVwcO','2019-12-09 14:28:06',current_date + current_time);
insert into user(userid,email,username,password,initialregistration,latestregistration)
         values (1889L,'hallo@email.de','User','$2a$15$w0IXxBihZJTHPt.y.xy84exSgISLzz06hTDk8PVkclYXtOz6tTaoq','2019-12-09 19:29:00',current_date + current_time);
insert into user(userid,email,username,password,initialregistration,latestregistration)
         values (2349L,'annak.dlugosch@googlemail.de','Anna','$2a$15$ooRhg7oqr4O5jCkA6U2dpOgFX5YR02aToXXlXGg62rDxeVq/keJ2q','2019-12-09 23:04:09',current_date + current_time);

--Insert into Post
insert into post(postid,poster,title,creationdate)
         values (1000L,2806L,'Mein erster Post','2019-12-09 15:30:00');
insert into post(postid,poster,title,creationdate)
         values (1001L,2806L,'Mein zweiter Post','2019-12-09 16:30:00');
insert into post(postid, poster, title,creationdate)
         values (1002L,2806L,'Mein dritter Post','2019-12-09 17:15:00');
