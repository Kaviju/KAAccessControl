package com.kaviju.accesscontrol.authentication;

import java.util.Arrays;

import er.extensions.foundation.ERXStringUtilities;

public class PasswordHash {
		public final PasswordHasher hasher;
		public final byte[] hash;
		public final byte[] salt;
		public final int iterations;
		
		static public PasswordHash parseHash(String hashString) {
			return parseHashWithDefaultHasher(hashString, null);
		}
		
		static public PasswordHash parseHashWithDefaultHasher(String hashString, PasswordHasher defaultHasher) {
			String[] hashParts = hashString.split(":");
			if (hashParts.length < 2) {
				if (defaultHasher == null) {
					throw new IllegalArgumentException("A default hasherCode should be specified for hash string without haserCode.");
				}
				else {
					byte[] hash = ERXStringUtilities.hexStringToByteArray(hashParts[0]);
					return new PasswordHash(defaultHasher, hash);
				}
			}
			return decodeCompleteHash(hashParts);
		}

		private static PasswordHash decodeCompleteHash(String[] hashParts) {
			PasswordHasher hasher = PasswordHasher.hasherWithCode(hashParts[0]);
			byte[] hash = ERXStringUtilities.hexStringToByteArray(hashParts[1]);
			byte[] salt = null;
			int iterations = 0;
			
			if (hashParts.length > 2) {
				salt = ERXStringUtilities.hexStringToByteArray(hashParts[2]);
			}
			if (hashParts.length > 3) {
				iterations = Integer.parseInt(hashParts[3]);
			}
			return new PasswordHash(hasher, hash, salt, iterations);
		}
		
		public PasswordHash(PasswordHasher hasher, byte[] hash) {
			this(hasher, hash, null, 0);
		}

		public PasswordHash(PasswordHasher hasher, byte[] hash, byte[] salt) {
			this(hasher, hash, salt, 0);
		}

		public PasswordHash(PasswordHasher hasher, byte[] hash, byte[] salt, int iterations) {
			if (hash == null) {
				throw new IllegalArgumentException("Cannot create a PasswordHash with a null hash.");
			}

			this.hasher = hasher;
			this.hash = hash;
			this.iterations = iterations;
			this.salt = salt;
		}
		
		public String hashAsHexString() {
			return ERXStringUtilities.byteArrayToHexString(hash);
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(hash);
			result = prime * result + hasher.hashCode();
			result = prime * result + iterations;
			result = prime * result + Arrays.hashCode(salt);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PasswordHash other = (PasswordHash) obj;
			if (!Arrays.equals(hash, other.hash))
				return false;
			if (!hasher.equals(other.hasher))
				return false;
			if (iterations != other.iterations)
				return false;
			if (!Arrays.equals(salt, other.salt))
				return false;
			return true;
		}

		public boolean fromSameHashingSpecs(PasswordHash other) {
			if (this == other)
				return true;
			if (other == null)
				return false;
			if (!hasher.equals(other.hasher))
				return false;
			if (iterations != other.iterations)
				return false;
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder(hasher.hasherCode());
			builder.append(":"+ERXStringUtilities.byteArrayToHexString(hash));
			if (salt != null) {
				builder.append(":"+ERXStringUtilities.byteArrayToHexString(salt));
				if (iterations != 0) {			
					builder.append(":"+iterations);
				}
			}
			return builder.toString();
		}
		
		public boolean hashEquals(PasswordHash obj) {
			return Arrays.equals(hash, obj.hash);
		}

		public boolean verifyPassword(String password) {			
			return hasher.verifyPasswordWithHash(password, this);
		}
		
	}