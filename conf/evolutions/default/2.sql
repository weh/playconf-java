# Inserting preselected speakers data

# --- !Ups

INSERT INTO speaker(id,name, email, bio, picture_url, twitter_id) values (
  1,
  'Guillaume Bort',
  'guillaumebort@someemail.com',
  'Guillaume Bort is the co-founder of Zenexity, the french Web Oriented...',
  'https://secure.gravatar.com/avatar/adcd749d588278dbd255068c1d4b20d3?s=200',
  'guillaumebort'
);

INSERT INTO proposal(title, proposal, type, is_approved, keywords, speaker_id) values (
  'History of playframework',
  'Walk down the memory lane of how playframework got started',
  0,
  true,
  'play1,play2',
  1
);

# --- !Downs